package com.agathakazak.chatme.presentation.main

import com.agathakazak.chatme.domain.UserResponse

sealed class LoginState(val response: UserResponse<String>? = null) {

    object Initial: LoginState()
    object IsLogged : LoginState()
    object NotLogged : LoginState()
    class IsLoggingError(response: UserResponse<String>) : LoginState(response)
    object Loading: LoginState()
}