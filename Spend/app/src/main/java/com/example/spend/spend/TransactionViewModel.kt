package com.example.spend.spend



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spend.auth.models.Category
import com.example.spend.auth.models.Transaction
import com.example.spend.spend.models.TransactionBody
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class TransactionViewModel(private val repository: TransactionRepository) : ViewModel() {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions
    val categories: StateFlow<List<Category>> = _categories
    private var errorMessage by mutableStateOf<String?>(null)


    fun getAllTransaction(userId: Number) {
        viewModelScope.launch {
            try {
                val response = repository.getAllTransaction(userId)
                _transactions.value = response
            } catch (e: Exception) {
                errorMessage = e.message
            }
        }
    }


    fun getCategories() {
        viewModelScope.launch {
            try {
                val response = repository.getCategories()
                _categories.value = response
            } catch (e: Exception) {
                errorMessage = e.message
            }
        }
    }

    fun createTransaction(transactionBody: TransactionBody) {
        viewModelScope.launch {
            try {
                val response = repository.createTransaction(transactionBody)
            } catch (e: Exception) {
                errorMessage = e.message
            }
        }
    }

}