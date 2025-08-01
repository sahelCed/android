import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.spend.auth.AuthViewModel
import com.example.spend.auth.models.Transaction
import com.example.spend.spend.TransactionCard
import com.example.spend.spend.TransactionDetailsModal
import com.example.spend.spend.TransactionViewModel

@Composable
fun SpendingContent(
    transactionViewModel: TransactionViewModel,
    authViewModel: AuthViewModel,
    navController: NavController
) {
    val transactions by transactionViewModel.transactions.collectAsState()
    var selectedTransaction by remember { mutableStateOf<Transaction?>(null) }

    LaunchedEffect(Unit) {
        authViewModel.user.value?.let { user ->
            transactionViewModel.getAllTransaction(user.id)
        }
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
                        onClickHandler = { selectedTransaction = transaction }
                    )
                }
            }
        }
    }

    TransactionDetailsModal(
        transaction = selectedTransaction,
        onDismiss = { selectedTransaction = null }
    )
}
