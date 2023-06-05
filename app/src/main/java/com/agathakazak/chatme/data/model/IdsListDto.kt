package com.agathakazak.chatme.data.model

import com.google.gson.annotations.SerializedName

data class IdsListDto(
    @SerializedName("ids") val ids: List<Int>
)
