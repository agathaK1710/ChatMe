package com.agathakazak.chatme.data.model

import com.google.gson.annotations.SerializedName

data class ChatDetailDto(
    @SerializedName("chatName") val chatName: String,
    @SerializedName("chatImageUrl") val chatImageUrl: String? = null,
    @SerializedName("stubImageColor") val stubImageColor: Int,
    @SerializedName("messages") val messages: List<MessageDto>,
    @SerializedName("members") val members: List<UserDto>
)