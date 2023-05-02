package com.agathakazak.chatme.domain.entity

data class UserLogin(
    val numberOrEmail: String,
    val password: String
)