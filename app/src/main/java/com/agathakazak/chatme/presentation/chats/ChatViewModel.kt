package com.agathakazak.chatme.presentation.chats

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agathakazak.chatme.domain.entity.MessageRequest
import com.agathakazak.chatme.domain.usecase.GetChatsForUserUseCase
import com.agathakazak.chatme.domain.usecase.GetUserByIdUseCase
import com.agathakazak.chatme.domain.usecase.GetUserByTokenUseCase
import com.agathakazak.chatme.domain.usecase.SendMessageUseCase
import com.google.gson.Gson
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val sendMessage: SendMessageUseCase,
    private val getUserByTokenUseCase: GetUserByTokenUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getChatsForUserUseCase: GetChatsForUserUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _chatScreenState = MutableLiveData<ChatScreenState>(ChatScreenState.Initial)
    val chatScreenState: LiveData<ChatScreenState> = _chatScreenState

    init {
        loadAllChats()
    }

    fun sendMessage(messageText: String, attachmentId: Int? = null, recipientId: Int) {
        val senderId = viewModelScope.async {
            getUserByTokenUseCase("Bearer " + getToken()!!)
        }
        viewModelScope.launch {
            val messageRequest = MessageRequest(
                senderId = senderId.await().id,
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

    private fun loadAllChats() {
        val senderId = viewModelScope.async {
            getUserByTokenUseCase("Bearer " + getToken()!!)
        }
        viewModelScope.launch {
            _chatScreenState.value = ChatScreenState.AllChats(
                getChatsForUserUseCase(senderId.await().id)
            )
        }
    }

    suspend fun getCompanionById(userId: Int) =
        withContext(viewModelScope.coroutineContext) {
            getUserByIdUseCase(userId)
        }

    private fun getToken(): String? {
        return sharedPreferences.getString(TOKEN, null)
    }

    companion object {
        private const val TOKEN = "token"
    }
}