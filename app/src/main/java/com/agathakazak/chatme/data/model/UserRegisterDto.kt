package com.agathakazak.chatme.data.model

import com.google.gson.annotations.SerializedName

data class UserRegisterDto(
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("stubImageColor") val stubImageColor: Int
)