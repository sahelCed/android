package com.example.spend.network
import com.example.spend.auth.models.Category
import com.example.spend.auth.models.GroupedTransaction
import com.example.spend.auth.models.Transaction
import com.example.spend.spend.models.CategoryBody
import com.example.spend.spend.models.TransactionBody
import com.example.spend.spend.models.UpdateTransactionBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TransactionService {
    @GET("/transactions/{userId}")
    suspend fun getAllTransaction(@Path("userId") userId: Number): List<GroupedTransaction>

    @GET("/transaction/{transactionId}")
    suspend fun getTransactionById(@Path("transactionId") userId: Number): Transaction

    @POST("/transactions")
    suspend fun createTransaction(
        @Body transactionBody: TransactionBody
    ): Transaction

    @GET("/categories")
    suspend fun getCategories(): List<Category>

    @POST("/categories")
    suspend fun createCategory(@Body categoryBody: CategoryBody): Category

    @GET("/sold/{userId}")
    suspend fun getBalance(@Path("userId") userId: Number): Double

    @DELETE("/transaction/{transactionId}")
    suspend fun deleteTransaction(
        @Path("transactionId") userId: Number
    ): Transaction

    @PATCH("/transactions/{transactionId}")
    suspend fun updateTransaction(
        @Path("transactionId") userId: Number,
        @Body body:UpdateTransactionBody
    ): Transaction

}