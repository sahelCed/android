package com.example.spend.spend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.spend.auth.models.Transaction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailsModal(
    transaction: Transaction?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (transaction != null) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.padding(top = 8.dp))

                Text(
                    text = "Montant : ${transaction.amount} €",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.padding(top = 4.dp))

                Text(
                    text = "Catégorie : ${transaction.categorieName ?: "Non spécifiée"}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.padding(top = 16.dp))

                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Fermer")
                }
            }
        }
    }
}