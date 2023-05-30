package com.agathakazak.chatme.presentation.messages

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agathakazak.chatme.domain.entity.Message
import com.agathakazak.chatme.domain.entity.MessageRequest
import com.agathakazak.chatme.domain.usecase.DeleteMessageUseCase
import com.agathakazak.chatme.domain.usecase.GetChatUseCase
import com.agathakazak.chatme.domain.usecase.GetUserByIdUseCase
import com.agathakazak.chatme.domain.usecase.GetUserByTokenUseCase
import com.agathakazak.chatme.domain.usecase.SendMessageUseCase
import com.google.gson.Gson
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
    private val deleteMessageUseCase: DeleteMessageUseCase,
    val recipientId: Int

) : ViewModel() {

    private val _messagesScreenState =
        MutableLiveData<MessagesScreenState>(MessagesScreenState.Initial)
    val messagesScreenState: LiveData<MessagesScreenState> = _messagesScreenState


    init {
        _messagesScreenState.value = MessagesScreenState.Loading
        loadMessages()
    }

    fun loadMessages() {
        val sender = viewModelScope.async {
            getUserByTokenUseCase("Bearer " + getToken()!!)
        }
        viewModelScope.launch {
            val messages = mutableStateListOf<Message>()
            messages.addAll(getChatUseCase(sender.await().id, recipientId))
            _messagesScreenState.value =
                MessagesScreenState.Messages(messages, getUserByIdUseCase(recipientId))
        }
    }

    fun deleteMessage(id: Int){
        viewModelScope.launch {
            deleteMessageUseCase(id)
        }
    }

    private fun getToken(): String? {
        return sharedPreferences.getString(TOKEN, null)
    }

    companion object {
        private const val TOKEN = "token"
    }


    fun sendMessage(messageText: String, attachmentId: Int? = null) {
        val sender = viewModelScope.async {
            getUserByTokenUseCase("Bearer " + getToken()!!)
        }
        viewModelScope.launch {
            val messageRequest = MessageRequest(
                senderId = sender.await().id,
                recipientId,
                messageText,
                attachmentId
            )
            try {
                sendMessage(messageRequest)
            } catch (e: HttpException) {
                val responseString = e.response()?.errorBody()?.string()
                val gson = Gson()
                val response = gson.fromJson(responseString, String::class.java)
            }
        }
    }
}