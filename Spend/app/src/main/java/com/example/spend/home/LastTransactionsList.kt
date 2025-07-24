package com.example.spend.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.spend.auth.models.Transaction
import com.example.spend.spend.TransactionCard
import com.example.spend.spend.TransactionDetailsModal
import com.example.spend.utils.formatDate

@Composable
fun LastTransactionsList(
    navController: NavController,
    transactions: List<Transaction>,
    modifier: Modifier,
) {
    var selectedTransaction by remember { mutableStateOf<Transaction?>(null) }

    fun onClickOnItemList(transaction: Transaction) {
        selectedTransaction = transaction
    }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        items(transactions) { t ->
            Spacer(modifier = Modifier.height(12.dp))
            t.createdAt?.let {
                Text(
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray,
                    text = formatDate(it)
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                TransactionCard(
                    transaction = t,
                    navController = navController,
                    onClickHandler = { onClickOnItemList(t) }
                )
            }
        }
    }
    TransactionDetailsModal(
        transaction = selectedTransaction,
        onDismiss = { selectedTransaction = null }
    )
}
