package com.example.spend.spend

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.spend.spend.models.CategoryBody
import com.example.spend.spend.models.UpdateTransactionBody


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditTransactionScreen(
    itemId:String,
    transactionViewModel:TransactionViewModel
){
    val transaction by transactionViewModel.transaction.collectAsState()
    val categories by transactionViewModel.categories.collectAsState()

    var category by remember { mutableStateOf(0) }
    var type by remember { mutableStateOf("EXPENSE") }

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    LaunchedEffect(transaction) {
        if (transaction != null) {
            title = transaction!!.title
            amount = transaction!!.amount.toString()
            //category = categories.filter { x -> x.name == transaction!!.categorieName }.first().id
            type = transaction!!.type.toString()
        }
    }

    LaunchedEffect(Unit) {
        transactionViewModel.let {
           transactionViewModel.getTransactionById(itemId.toInt())
            transactionViewModel.getCategories()
        }
    }
    Scaffold(
        Modifier.padding(12.dp),
        topBar = {
            TopAppBar(
                title = { Text("Editer une Transaction") }
            )
        }
    ) {
        innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Nom de la transaction") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Montant (€)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
//            CategorySelector(
//                categories = categories,
//                selectedCategory = category,
//                onCategorySelected = { category = it }
//            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("EXPENSE", "INCOME").forEach { option ->
                    val isSelected = type == option
                    Button(
                        onClick = { type = option },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) {
                                if (option == "EXPENSE") Color(0xFFD32F2F) else Color(0xFF388E3C)
                            } else Color.LightGray
                        )
                    ) {
                        Text(option)
                    }
                }
            }
            Button(
                onClick = {
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Valider")
            }
        }
    }
}