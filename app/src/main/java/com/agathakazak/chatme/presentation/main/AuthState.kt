package com.agathakazak.chatme.presentation.main

sealed class AuthState {
    object Initial: AuthState()
    object Authorized: AuthState()
    object NotAuthorized: AuthState()
}