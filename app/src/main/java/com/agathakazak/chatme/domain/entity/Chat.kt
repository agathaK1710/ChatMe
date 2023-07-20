package com.agathakazak.chatme.domain.entity

data class Chat(
    val id: Int,
    val chatName: String,
    val chatImageUrl: String?,
    val stubImageColor: Int,
    val lastMessage: Message
)