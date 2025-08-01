package com.example.spend.auth.models

data class Transaction(
    val id: Int,
    val amount: Double,
    val userId: Int,
    val type: TransactionType,
    val categorieName: String,
    val title: String,
    val createdAt: String?
)

enum class TransactionType {
    INCOME,
    EXPENSE
}

data class GroupedTransaction(
    val date:String,
    val transactions: List<Transaction>
)