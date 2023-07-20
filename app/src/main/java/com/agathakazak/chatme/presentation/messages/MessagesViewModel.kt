package com.agathakazak.chatme.presentation.messages

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agathakazak.chatme.R
import com.agathakazak.chatme.data.network.WebSocketClient
import com.agathakazak.chatme.domain.entity.Message
import com.agathakazak.chatme.domain.entity.MessageRequest
import com.agathakazak.chatme.domain.entity.MessageType
import com.agathakazak.chatme.domain.entity.SocketMessage
import com.agathakazak.chatme.domain.entity.User
import com.agathakazak.chatme.domain.usecase.GetChatUseCase
import com.agathakazak.chatme.domain.usecase.GetUserByTokenUseCase
import com.agathakazak.chatme.navigation.Screen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.lang.reflect.Type
import javax.inject.Inject


class MessagesViewModel @Inject constructor(
    private val getUserByTokenUseCase: GetUserByTokenUseCase,
    private val getChatUseCase: GetChatUseCase,
    private val sharedPreferences: SharedPreferences,
    val chatId: Int,
    private val notificationManager: NotificationManager,
    private val notificationBuilder: NotificationCompat.Builder

) : ViewModel() {
    companion object {
        private const val TOKEN = "token"
        const val KEY_TEXT_REPLY = "key_text_reply"
        const val channelId = "Message"
        const val notificationId = 1
        const val MESSAGE = "message"
    }

    private var user: Deferred<User>
    private lateinit var socket: WebSocket

    private val _messagesScreenState =
        MutableLiveData<MessagesScreenState>(MessagesScreenState.Initial)
    val messagesScreenState: LiveData<MessagesScreenState> = _messagesScreenState

    private var _messages: SnapshotStateList<Message> = mutableStateListOf()
    val messages: List<Message> = _messages

    private val _repliedMessage =
        MutableLiveData<String?>(sharedPreferences.getString(MESSAGE, null))
    val repliedMessage: LiveData<String?> = _repliedMessage

    init {
        user = viewModelScope.async {
            getUserByTokenUseCase("Bearer " + getToken()!!)
        }
        viewModelScope.launch {
            socket = createSocket()
        }
        sharedPreferences.edit().remove(MESSAGE).apply()
        _messagesScreenState.value = MessagesScreenState.Loading
        loadMessages()
    }

    fun changeSelectedStatus(index: Int, isSelected: Boolean) {
        _messages[index].isSelected = isSelected
    }

    private suspend fun createSocket(): WebSocket {
        val userId = user.await().id
        val socketListener = object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                viewModelScope.launch {
                    val message = Gson().fromJson(text, SocketMessage::class.java)
                    when (message.type) {
                        MessageType.INSERT -> {
                            _messages.add(getChatUseCase(chatId).messages.last())
                        }

                        MessageType.DELETE -> {
                            val jsonArr = Gson().toJson(message.data)
                            val listType: Type = object : TypeToken<List<Int>>() {}.type
                            val selectedMessagesIds: List<Int> = Gson().fromJson(jsonArr, listType)
                            selectedMessagesIds.forEach { selectedMessageId ->
                                _messages.removeIf { selectedMessageId == it.id }
                            }
                        }

                        MessageType.UPDATE -> {

                        }
                    }
                }
            }
        }
        return WebSocketClient.createWebSocket(chatId, userId, socketListener)
    }

    fun unselectAllMessages() {
        _messages.forEach { message ->
            if (message.isSelected) message.isSelected = false
        }
    }

    fun getSelectedMessages() = _messages.filter { it.isSelected }

    private fun loadMessages() {
        viewModelScope.launch {
            val userId = user.await().id
            val chat = getChatUseCase(chatId)
            _messages.addAll(chat.messages)
            _messagesScreenState.value =
                MessagesScreenState.Messages(chat, userId)
        }
    }

    fun deleteSelectedMessages() {
        viewModelScope.launch {
            socket.send(
                Gson().toJson(
                    SocketMessage(
                        getSelectedMessages().map { it.id },
                        MessageType.DELETE
                    )
                )
            )
        }
    }

    private fun getToken(): String? {
        return sharedPreferences.getString(TOKEN, null)
    }

    fun sendMessage(messageText: String, attachmentId: Int? = null) {
        viewModelScope.launch {
            val messageRequest = MessageRequest(
                senderId = user.await().id,
                chatId,
                messageText,
                attachmentId
            )
            socket.send(Gson().toJson(SocketMessage(messageRequest, MessageType.INSERT)))

        }
    }

//    fun readMessages() = viewModelScope.launch {
//        readMessagesUseCase(recipientId)
//    }

    fun messageNotification(
        recipientId: Int,
        sender: User,
        lastMessage: String,
        context: Context
    ) {
        createNotificationChannel(channelId, notificationManager)

        val intent = Intent(
            Intent.ACTION_VIEW,
            "chatme://chats_graph/${Screen.KEY_CHAT_ID}=$recipientId".toUri()
        )

        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(
                intent
            )
            getPendingIntent(1234, PendingIntent.FLAG_IMMUTABLE)
        }

        val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
            setLabel(context.resources.getString(R.string.reply_label))
            build()
        }

        val replyIntent = Intent(context, NotificationReceiver::class.java)

        val replyPendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            replyIntent,
            PendingIntent.FLAG_MUTABLE
        )

        val action = NotificationCompat.Action.Builder(
            R.drawable.send,
            context.resources.getString(R.string.reply_label), replyPendingIntent
        )
            .addRemoteInput(remoteInput)
            .build()

        val person = Person.Builder().setName("${sender.firstName} ${sender.lastName}").build()
        val notificationStyle = NotificationCompat.MessagingStyle(person).addMessage(
            lastMessage, System.currentTimeMillis(), person
        )
        val builder = notificationBuilder.setChannelId(channelId)
            .setSmallIcon(R.drawable.message)
            .setStyle(notificationStyle)
            .setContentIntent(pendingIntent)
            .addAction(action)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationManagerCompat.from(context).apply {
            notificationManager.notify(notificationId, builder.build())
        }
    }


    private fun createNotificationChannel(
        channelId: String,
        notificationManager: NotificationManager
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MessageChannel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance)
            notificationManager.createNotificationChannel(channel)
        }
    }
}