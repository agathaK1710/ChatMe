package com.agathakazak.chatme.presentation.messages

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agathakazak.chatme.domain.entity.Message
import com.agathakazak.chatme.domain.entity.MessageRequest
import com.agathakazak.chatme.domain.entity.User
import com.agathakazak.chatme.domain.usecase.DeleteMessagesUseCase
import com.agathakazak.chatme.domain.usecase.GetChatUseCase
import com.agathakazak.chatme.domain.usecase.GetUserByIdUseCase
import com.agathakazak.chatme.domain.usecase.GetUserByTokenUseCase
import com.agathakazak.chatme.domain.usecase.SendMessageUseCase
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class MessagesViewModel @Inject constructor(
    private val getUserByTokenUseCase: GetUserByTokenUseCase,
    private val getChatUseCase: GetChatUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val sendMessage: SendMessageUseCase,
    private val sharedPreferences: SharedPreferences,
    private val deleteMessageUseCase: DeleteMessagesUseCase,
    val recipientId: Int

) : ViewModel() {
    private lateinit var user: Deferred<User>

    private val _messagesScreenState =
        MutableLiveData<MessagesScreenState>(MessagesScreenState.Initial)
    val messagesScreenState: LiveData<MessagesScreenState> = _messagesScreenState

    private var _messages: SnapshotStateList<Message> = mutableStateListOf()
    val messages: List<Message> = _messages

    init {
        user = viewModelScope.async {
            getUserByTokenUseCase("Bearer " + getToken()!!)
        }
        _messagesScreenState.value = MessagesScreenState.Loading
        loadMessages()
    }

    fun changeSelectedStatus(index: Int, isSelected: Boolean) {
        _messages[index].isSelected = isSelected
    }

    fun unselectAllMessages() {
        _messages.forEach { message ->
            if (message.isSelected) message.isSelected = false
        }
    }

    fun getSelectedMessages() = _messages.filter { it.isSelected }

    private fun loadMessages() {
        viewModelScope.launch {
            _messages.addAll(getChatUseCase(user.await().id, recipientId))
            _messagesScreenState.value =
                MessagesScreenState.Messages(getUserByIdUseCase(recipientId))
        }
    }

    fun deleteSelectedMessages() {
        viewModelScope.launch {
            val selectedMessages = getSelectedMessages()
            deleteMessageUseCase(selectedMessages.map { it.id!! })
            _messages.removeAll(selectedMessages)
        }
    }

    private fun getToken(): String? {
        return sharedPreferences.getString(TOKEN, null)
    }

    companion object {
        private const val TOKEN = "token"
    }


    fun sendMessage(messageText: String, attachmentId: Int? = null) {
        viewModelScope.launch {
            val messageRequest = MessageRequest(
                senderId = user.await().id,
                recipientId,
                messageText,
                attachmentId
            )
            try {
                sendMessage(messageRequest)
                _messages.add(getChatUseCase(user.await().id, recipientId).last())
            } catch (e: HttpException) {
                val responseString = e.response()?.errorBody()?.string()
                val gson = Gson()
                val response = gson.fromJson(responseString, String::class.java)
            }
        }
    }
}