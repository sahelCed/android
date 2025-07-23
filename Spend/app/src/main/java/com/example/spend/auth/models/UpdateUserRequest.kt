package com.example.spend.auth.models

data class UpdateUserRequest (
    val userId: Int,
    val email: String,
    val password: String

)