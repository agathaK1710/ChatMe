package com.agathakazak.chatme.domain.entity

data class UserResponse<T>(
    val success: Boolean,
    val data: T
)
