package com.example.composecampgroup4.data.network.api

import com.example.composecampgroup4.data.network.dto.JarDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("bank/jar/{longJarId}")
    suspend fun getJarData(@Path("longJarId") longJarId: String): JarDto
}