package com.agathakazak.chatme.data.model

import com.google.gson.annotations.SerializedName

data class UserLoginDto(
    @SerializedName("numberOrEmail") val numberOrEmail: String,
    @SerializedName("password") val password: String
)