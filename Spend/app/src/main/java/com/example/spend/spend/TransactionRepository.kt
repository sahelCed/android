package com.example.spend.spend

import com.example.spend.auth.models.Category
import com.example.spend.auth.models.LoginResponse
import com.example.spend.auth.models.Transaction
import com.example.spend.network.TransactionService
import com.example.spend.spend.models.TransactionBody

class TransactionRepository(private val transactionService: TransactionService) {
    suspend fun getAllTransaction(userId: Number): List<Transaction> {
        val transactions =  transactionService.getAllTransaction(userId)
        return transactions
    }

    suspend fun getCategories(): List<Category> {
        val categories =  transactionService.getCategories()
        return categories
    }

    suspend fun createTransaction(transactionBody: TransactionBody): Transaction {
        return  transactionService.createTransaction(
            transactionBody = transactionBody
        )
    }
}