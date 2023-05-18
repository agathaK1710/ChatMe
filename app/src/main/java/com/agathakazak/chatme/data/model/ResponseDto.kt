package com.agathakazak.chatme.data.model

import com.google.gson.annotations.SerializedName

data class ResponseDto<T> (
    @SerializedName("data") val data: T
)