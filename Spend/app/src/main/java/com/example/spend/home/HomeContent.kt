package com.example.spend.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spend.auth.AuthViewModel
import com.example.spend.spend.TransactionViewModel

@SuppressLint("DefaultLocale")
@Composable
fun HomeContent(
    authViewModel: AuthViewModel = viewModel(),
    transactionViewModel: TransactionViewModel = viewModel()
) {

    val user by authViewModel.user.collectAsState()
    val balance by transactionViewModel.balance.collectAsState()

    LaunchedEffect(user?.id) {
        Log.d("USER", "${user?.id} , ${user?.email}")
        user?.let {
            transactionViewModel.getBalance(it.id)
        }
    }

    Column {
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
            Text(text = "Dernières transactions")
        }
    }


}