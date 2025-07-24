package com.example.spend.spend

import com.example.spend.auth.models.Category
import com.example.spend.auth.models.GroupedTransaction
import com.example.spend.auth.models.LoginResponse
import com.example.spend.auth.models.Transaction
import com.example.spend.network.TransactionService
import com.example.spend.spend.models.CategoryBody
import com.example.spend.spend.models.TransactionBody
import com.example.spend.spend.models.UpdateTransactionBody

class TransactionRepository(private val transactionService: TransactionService) {
    suspend fun getAllTransaction(userId: Number): List<GroupedTransaction> {
        val transactions =  transactionService.getAllTransaction(userId)
        return transactions
    }

    suspend fun getCategories(): List<Category> {
        val categories =  transactionService.getCategories()
        return categories
    }

    suspend fun getBalance(userId: Number): Double {
        val balance = transactionService.getBalance(userId)
        return balance
    }

    suspend fun getLastTransactions(userId: Number): List<Transaction> {
        val transactions = transactionService.getLastTransactions(userId)
        return transactions
    }

    suspend fun createTransaction(transactionBody: TransactionBody): Transaction {
        return  transactionService.createTransaction(
            transactionBody = transactionBody
        )
    }

    suspend fun createCategory(categoryBody: CategoryBody): Category {
        return  transactionService.createCategory(
            categoryBody = categoryBody
        )
    }

    suspend fun deleteTransaction(transactionId: Int): Transaction {
        return  transactionService.deleteTransaction(
            transactionId
        )
    }

    suspend fun updateTransaction(transactionId: Int,body:UpdateTransactionBody): Transaction {
        return  transactionService.updateTransaction(
            transactionId,
            body
        )
    }

    suspend fun getTransactionById(transactionId: Int): Transaction {
        return  transactionService.getTransactionById(
            transactionId
        )
    }
}