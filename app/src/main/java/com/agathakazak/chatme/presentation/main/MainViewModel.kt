package com.agathakazak.chatme.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.agathakazak.chatme.data.repository.UserRepository
import com.agathakazak.chatme.domain.User
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository()

    fun registerUser(user: User) {
        viewModelScope.launch {
            userRepository.registerUser(user)
        }
    }

}