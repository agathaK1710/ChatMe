package com.agathakazak.chatme.presentation.login

sealed class LoginState(val response: String? = null) {

    object Initial: LoginState()
    object IsLogged : LoginState()
    object NotLogged : LoginState()
    class IsLoggingError(response: String) : LoginState(response)
    object Loading: LoginState()
}