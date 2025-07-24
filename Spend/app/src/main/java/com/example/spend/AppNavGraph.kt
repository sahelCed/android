package com.example.spend
import com.example.spend.auth.LoginScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spend.auth.AuthRepository
import com.example.spend.auth.AuthViewModel
import com.example.spend.auth.AuthViewModelFactory
import com.example.spend.auth.RegisterScreen
import com.example.spend.home.HomeScreen
import com.example.spend.network.RetrofitHttpClient
import com.example.spend.spend.AddSpendScreen
import com.example.spend.spend.TransactionRepository
import com.example.spend.spend.TransactionViewModel
import com.example.spend.spend.TransactionViewModelFactory
import com.example.spend.spend.EditTransactionScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    val authRepository = remember { AuthRepository(RetrofitHttpClient.authService) }
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(authRepository)
    )

    val transactionRepository = remember { TransactionRepository(RetrofitHttpClient.transactionService) }
    val transactionViewModel: TransactionViewModel = viewModel(
        factory = TransactionViewModelFactory(transactionRepository)
    )

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel,
            )
        }
        composable("register") {
            RegisterScreen(
                authViewModel = authViewModel,
                navController = navController,

            )
        }
        composable("home") {
            HomeScreen(
                authViewModel = authViewModel,
                navController = navController,
                transactionViewModel = transactionViewModel
            )
        }
        composable("addSpend") {
            AddSpendScreen(
                authViewModel = authViewModel,
                transactionViewModel = transactionViewModel,
                navController = navController
            )
        }

        composable("edit/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            EditTransactionScreen(
                itemId = itemId!!,
                transactionViewModel
            )
        }
    }
}
