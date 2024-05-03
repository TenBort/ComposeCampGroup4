package com.example.composecampgroup4.data.network.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiFactory {
    private const val HANDLER_BASE_URL = "https://send.monobank.ua/"
    private const val BASE_URL = "https://api.monobank.ua/"

    private val handlerRetrofit = Retrofit.Builder()
        .baseUrl(HANDLER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val handlerApiService: HandlerApiService = handlerRetrofit.create()

    val apiService: ApiService = retrofit.create()
}