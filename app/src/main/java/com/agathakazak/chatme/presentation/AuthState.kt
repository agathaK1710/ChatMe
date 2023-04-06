package com.agathakazak.chatme.presentation

sealed class AuthState {
    object Initial: AuthState()
    object Authorized: AuthState()
    object NotAuthorized: AuthState()
}