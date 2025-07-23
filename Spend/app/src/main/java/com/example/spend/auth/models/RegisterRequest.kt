package com.example.spend.auth.models

data class RegisterRequest(
    val email: String,
    val password: String,
)