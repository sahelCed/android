package com.example.spend.auth.models

data class LoginRequest(
    val email: String,
    val password: String
)