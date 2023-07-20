package com.agathakazak.chatme.data.model

import com.google.gson.annotations.SerializedName


data class MessageRequestDto(
    @SerializedName("senderId") val senderId: Int,
    @SerializedName("chatId") val chatId: Int,
    @SerializedName("messageText") val messageText: String,
    @SerializedName("attachmentId") val attachmentId: Int? = null
)