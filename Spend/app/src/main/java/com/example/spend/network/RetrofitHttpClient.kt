package com.example.spend.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHttpClient {
    private const val BASE_URL = "http://93.127.158.91:3000"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Configured HTTP client's base url
            .addConverterFactory(GsonConverterFactory.create()) // JSON -> DataClass converter
            .build()
    }

    val authService: AuthService by lazy {
        instance.create(AuthService::class.java)
    }

    val transactionService: TransactionService by lazy {
        instance.create(TransactionService::class.java)
    }
}