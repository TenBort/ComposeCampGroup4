package com.example.composecampgroup4.data.network.api

import com.example.composecampgroup4.data.network.dto.HandlerResponseDto
import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST

interface HandlerApiService {
    @POST("api/handler")
    suspend fun postData(@Body request: HandlerRequestData): HandlerResponseDto
}

data class HandlerRequestData(
    @SerializedName("c") val c: String = "hello",
    @SerializedName("clientId") val clientId: String,
    @SerializedName("Pc") val pc: String = "random"
)