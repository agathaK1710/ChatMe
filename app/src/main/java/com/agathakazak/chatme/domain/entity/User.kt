package com.agathakazak.chatme.domain.entity

data class User(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val password: String?,
    val imageUrl: String?
)