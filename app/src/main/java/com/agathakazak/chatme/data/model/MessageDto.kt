package com.agathakazak.chatme.data.model

import com.google.gson.annotations.SerializedName

data class MessageDto(
    @SerializedName("sender_id") val senderId: Int,
    @SerializedName("recipient_id") val recipientId: Int? = null,
    @SerializedName("message_text") val messageText: String,
    @SerializedName("date") val date: Long,
    @SerializedName("attachment_id") val attachmentId: Int? = null,
    @SerializedName("is_unread") val isUnread: Int? = null,
)