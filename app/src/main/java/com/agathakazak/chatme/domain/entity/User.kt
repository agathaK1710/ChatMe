package com.agathakazak.chatme.domain.entity

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val imageUrl: String?
)