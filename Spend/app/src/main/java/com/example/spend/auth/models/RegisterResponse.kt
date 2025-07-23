package com.example.spend.auth.models

data class RegisterResponse(
    val data: User,
    val token: String,
    val userId: Int,
)