package com.agathakazak.chatme.data.model

import com.google.gson.annotations.SerializedName

data class SimpleResponseDto<T>(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: T
)