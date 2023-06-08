package com.agathakazak.chatme.domain.entity

import java.util.Date


data class Message(
    val id: Int? = null,
    val senderId: Int,
    val recipientId: Int,
    val messageText: String,
    val date: Date,
    val attachmentId: Int? = null,
    val isUnread: Boolean,
    var isSelected: Boolean = false
)