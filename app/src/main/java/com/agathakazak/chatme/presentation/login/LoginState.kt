package com.agathakazak.chatme.presentation.login

import com.agathakazak.chatme.domain.entity.SimpleResponse

sealed class LoginState(val response: SimpleResponse<String>? = null) {

    object Initial: LoginState()
    object IsLogged : LoginState()
    object NotLogged : LoginState()
    class IsLoggingError(response: SimpleResponse<String>) : LoginState(response)
    object Loading: LoginState()
}