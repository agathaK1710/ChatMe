package com.agathakazak.chatme.presentation.chats

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agathakazak.chatme.domain.usecase.GetChatsForUserUseCase
import com.agathakazak.chatme.domain.usecase.GetUserByTokenUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatsViewModel @Inject constructor(
    private val getUserByTokenUseCase: GetUserByTokenUseCase,
    private val getChatsForUserUseCase: GetChatsForUserUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _chatScreenState = MutableLiveData<AllChatsScreenState>(AllChatsScreenState.Initial)
    val chatScreenState: LiveData<AllChatsScreenState> = _chatScreenState

    init {
        _chatScreenState.value = AllChatsScreenState.Loading
        loadAllChats()
    }

    private fun loadAllChats() {
        val sender = viewModelScope.async {
            getUserByTokenUseCase("Bearer " + getToken()!!)
        }
        viewModelScope.launch {
            _chatScreenState.value = AllChatsScreenState.Chats(
                getChatsForUserUseCase(sender.await().id)
            )
        }
    }

    private fun getToken(): String? {
        return sharedPreferences.getString(TOKEN, null)
    }

    companion object {
        private const val TOKEN = "token"
    }
}