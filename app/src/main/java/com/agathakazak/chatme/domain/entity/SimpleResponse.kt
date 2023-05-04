package com.agathakazak.chatme.domain.entity

data class SimpleResponse<T>(
    val success: Boolean,
    val data: T
)
