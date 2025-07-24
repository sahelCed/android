package com.example.spend.spend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.spend.auth.AuthViewModel
import com.example.spend.auth.models.Transaction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpendingContent(
    transactionViewModel: TransactionViewModel,
    authViewModel: AuthViewModel,
    navController: NavController
){
    val transactions by transactionViewModel.transactions.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheet by remember { mutableStateOf(false) }
    var selectedTransaction by remember { mutableStateOf<Transaction?>(null) }

    fun onClickOnItemList(transaction:Transaction){
        selectedTransaction = transaction
    }

    LaunchedEffect(Unit) {
        authViewModel.user.value?.let { it -> transactionViewModel.getAllTransaction(it.id) }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)

    ) {
        items(transactions) { gt ->
            Spacer(modifier = Modifier.height(12.dp))
            Text(gt.date)
            Spacer(modifier = Modifier.height(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                gt.transactions.reversed().map { transaction ->
                    TransactionCard(
                        transaction = transaction,
                        navController = navController,
                        onClickHandler = { onClickOnItemList(transaction) }
                    )
                }
            }
        }
    }
    if (selectedTransaction != null) {
        ModalBottomSheet(
            onDismissRequest = {
                showSheet = false
                selectedTransaction = null
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = selectedTransaction?.title ?: "",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.padding(top = 8.dp))

                Text(
                    text = "Montant : ${selectedTransaction?.amount} €",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.padding(top = 4.dp))

                Text(
                    text = "Catégorie : ${selectedTransaction?.categorieName ?: "Non spécifiée"}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.padding(top = 16.dp))

                OutlinedButton(
                    onClick = {
                        selectedTransaction = null
                        showSheet = false
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Fermer")
                }
            }
        }
    }
}
