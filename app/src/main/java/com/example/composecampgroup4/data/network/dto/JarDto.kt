package com.example.composecampgroup4.data.network.dto

import com.google.gson.annotations.SerializedName

data class JarDto(
    @SerializedName("jarId") val jarId: String,
    @SerializedName("amount") val amount: Long,
    @SerializedName("goal") val goal: Long,
    @SerializedName("ownerIcon") val ownerIcon: String,
    @SerializedName("title") val title: String,
    @SerializedName("currency") val currency: Int,
    @SerializedName("description") val description: String,
    @SerializedName("closed") val closed: Boolean,
)