package com.example.spend.network
import com.example.spend.auth.models.LoginRequest
import com.example.spend.auth.models.LoginResponse
import com.example.spend.auth.models.RegisterRequest
import com.example.spend.auth.models.RegisterResponse
import com.example.spend.auth.models.UpdateUserRequest
import com.example.spend.auth.models.UpdateUserResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {
    @POST("/signin")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("/signup")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("/updateUser")
    suspend fun update(
        @Body registerRequest: UpdateUserRequest
    ): UpdateUserResponse
}