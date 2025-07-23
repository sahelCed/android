package com.example.spend.spend.models

import com.example.spend.auth.models.TransactionType

data class TransactionBody(
    val title: String,
    val amount: Double,
    val categoryId: Int,
    val userId: Int,
    val type: TransactionType
)
