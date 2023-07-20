package com.agathakazak.chatme.data.model

import com.google.gson.annotations.SerializedName

data class ChatDto(
    @SerializedName("chatId") val id: Int,
    @SerializedName("chatName") val chatName: String,
    @SerializedName("chatImageUrl") val chatImageUrl: String? = null,
    @SerializedName("stubImageColor") val stubImageColor: Int,
    @SerializedName("lastMessage") val lastMessage: MessageDto
)