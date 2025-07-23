package com.example.spend.auth

import com.example.spend.auth.models.LoginRequest
import com.example.spend.auth.models.LoginResponse
import com.example.spend.auth.models.RegisterRequest
import com.example.spend.auth.models.RegisterResponse
import com.example.spend.auth.models.UpdateUserRequest
import com.example.spend.auth.models.UpdateUserResponse
import com.example.spend.network.AuthService

class AuthRepository(private val authService: AuthService) {

    suspend fun login(email: String, password: String): LoginResponse {
        val loginRequest = LoginRequest(email, password)
        return authService.login(loginRequest)
    }

    suspend fun register(email: String, password: String): RegisterResponse {
        val registerRequest = RegisterRequest(email, password)
        return authService.register(registerRequest)
    }

    suspend fun updateUser(email: String, password: String,userId:Int):UpdateUserResponse {
        val updateUserRequest = UpdateUserRequest(userId,email,password)
        return authService.update(updateUserRequest)
    }
}