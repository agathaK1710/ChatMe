package com.agathakazak.chatme.data.model

import com.google.gson.annotations.SerializedName

data class MessageDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("senderId") val senderId: Int,
    @SerializedName("chatId") val chatId: Int,
    @SerializedName("messageText") val messageText: String,
    @SerializedName("date") val date: Long,
    @SerializedName("attachmentId") val attachmentId: Int? = null,
    @SerializedName("isUnread") val isUnread: Boolean
)