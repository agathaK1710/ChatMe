package com.agathakazak.chatme.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)