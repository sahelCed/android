package com.example.spend.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.spend.auth.AuthViewModel
import com.example.spend.profile.ProfileContent
import com.example.spend.spend.SpendingContent
import com.example.spend.spend.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    authViewModel: AuthViewModel = viewModel(),
    transactionViewModel: TransactionViewModel = viewModel(),
    navController: NavController
    ) {

    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Accueil","Dépenses", "Profil")
    val user by authViewModel.user.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(user?.email.toString())
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addSpend")
            }) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter")
            }
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = when (index) {
                                    0 -> Icons.Default.Home
                                    1 -> Icons.Default.ShoppingCart
                                    else -> Icons.Default.Person
                                },
                                contentDescription = item
                            )
                        },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (selectedItem) {
                0 -> HomeContent()
                1 -> SpendingContent(
                    transactionViewModel = transactionViewModel,
                    authViewModel = authViewModel,
                    navController = navController
                )
                2 -> ProfileContent(
                    authViewModel = authViewModel,
                    navController = navController
                )
            }
        }
    }
}