package com.agathakazak.chatme.domain.entity

data class MessageRequest(
   val senderId: Int,
   val recipientId: Int,
   val messageText: String,
   val attachmentId: Int? = null
)