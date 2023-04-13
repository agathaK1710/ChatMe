package com.agathakazak.chatme.domain

data class UserResponse<T>(
    val success: Boolean,
    val data: T
)
