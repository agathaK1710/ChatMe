package com.agathakazak.chatme.data.model

import com.google.gson.annotations.SerializedName

data class UserLoginDto(
    @SerializedName("number_or_email") val numberOrEmail: String,
    @SerializedName("password") val password: String
)