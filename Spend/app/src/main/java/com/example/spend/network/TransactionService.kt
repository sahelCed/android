package com.example.spend.network
import com.example.spend.auth.models.Category
import com.example.spend.auth.models.Transaction
import com.example.spend.spend.models.TransactionBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TransactionService {
    @GET("/transactions/{userId}")
    suspend fun getAllTransaction(@Path("userId") userId: Number): List<Transaction>

    @POST("/transactions")
    suspend fun createTransaction(
        @Body transactionBody: TransactionBody
    ): Transaction

    @GET("/categories")
    suspend fun getCategories(): List<Category>
}