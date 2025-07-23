package com.example.spend.spend

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.spend.auth.AuthViewModel

@Composable
fun SpendingContent(
    transactionViewModel: TransactionViewModel,
    authViewModel: AuthViewModel
){
    val transactions by transactionViewModel.transactions.collectAsState()

    LaunchedEffect(Unit) {
        authViewModel.user.value?.let { it -> transactionViewModel.getAllTransaction(it.id) }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(transactions) { transaction ->
            TransactionCard(transaction = transaction)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}