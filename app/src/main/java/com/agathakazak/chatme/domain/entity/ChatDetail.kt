package com.agathakazak.chatme.domain.entity

data class ChatDetail(
    val chatName: String,
    val chatImageUrl: String?,
    val stubImageColor: Int,
    val members: List<User>,
    val messages: List<Message>
)