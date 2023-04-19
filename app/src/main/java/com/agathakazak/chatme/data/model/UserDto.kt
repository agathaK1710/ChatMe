package com.agathakazak.chatme.data.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("image_url") val imageUrl: String?
)