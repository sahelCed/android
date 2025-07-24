package com.example.spend.home

import MonthlyTransactionsGraph
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.spend.auth.AuthViewModel
import com.example.spend.spend.TransactionViewModel

@SuppressLint("DefaultLocale")
@Composable
fun HomeContent(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    transactionViewModel: TransactionViewModel = viewModel(),
    onSeeAllClick: () -> Unit
) {

    val transactions by transactionViewModel.lastTransactions.collectAsState()
    val user by authViewModel.user.collectAsState()
    val balance by transactionViewModel.balance.collectAsState()

    LaunchedEffect(user?.id) {
        Log.d("USER", "${user?.id} , ${user?.email}")
        user?.let {
            transactionViewModel.getBalance(it.id)
            transactionViewModel.getLastTransactions(it.id)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                text = "Solde : ${String.format("%.2f", balance)} €"
            )
        }
        Box(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                fontStyle = FontStyle.Italic,
                text = "Dernières transactions"
            )
        }

        LastTransactionsList(
            navController = navController,
            transactions = transactions.take(3),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp, top = 28.dp)
                .clickable { onSeeAllClick()},
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Color(0xFF3F51B5),
            text = "Voir toutes les dépenses",
            textDecoration = TextDecoration.Underline,
        )

        MonthlyTransactionsGraph(
            transactions = transactions,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
    }



}