package com.example.composecampgroup4.data.network.dto

import com.google.gson.annotations.SerializedName

data class HandlerResponseDto(
    @SerializedName("extJarId") val longJarId: String,
    @SerializedName("ownerName") val ownerName: String
)