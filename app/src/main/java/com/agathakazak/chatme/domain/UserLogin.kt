package com.agathakazak.chatme.domain

data class UserLogin(
    val numberOrEmail: String,
    val password: String
)