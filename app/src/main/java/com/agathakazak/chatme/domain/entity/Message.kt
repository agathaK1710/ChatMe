package com.agathakazak.chatme.domain.entity


data class Message(
    val senderId: Int,
    val recipientId: Int? = null,
    val messageText: String,
    val date: Long,
    val attachmentId: Int? = null,
    val isUnread: Int? = null,
)