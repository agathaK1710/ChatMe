package com.agathakazak.chatme.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agathakazak.chatme.data.repository.UserRepository
import com.agathakazak.chatme.domain.User
import com.agathakazak.chatme.domain.UserLogin
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val userRepository = UserRepository()


    fun registerUser(user: User) { //todo check userResponse
        viewModelScope.launch {
            userRepository.registerUser(user)
        }
    }

    fun loginUser(userLogin: UserLogin) { //todo check userResponse
        viewModelScope.launch {
            userRepository.loginUser(userLogin)
        }
    }
}