package com.agathakazak.chatme.data.model

import com.google.gson.annotations.SerializedName

data class ChatDto(
    @SerializedName("companionId") val companionId: Int,
    @SerializedName("lastMessage") val lastMessage: String,
    @SerializedName("isUnread") val isUnread: Boolean
)