package com.example.spend.spend



import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spend.auth.models.Category
import com.example.spend.auth.models.GroupedTransaction
import com.example.spend.auth.models.Transaction
import com.example.spend.spend.models.CategoryBody
import com.example.spend.spend.models.TransactionBody
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class TransactionViewModel(private val repository: TransactionRepository) : ViewModel() {

    private val _transactions = MutableStateFlow<List<GroupedTransaction>>(emptyList())
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val transactions: StateFlow<List<GroupedTransaction>> = _transactions

    private val _balance = MutableStateFlow<Double>(0.0)

    val balance:  StateFlow<Double> = _balance
    val categories: StateFlow<List<Category>> = _categories
    private var errorMessage by mutableStateOf<String?>(null)

    private val _transactionAdded = MutableStateFlow(false)
    val transactionAdded: StateFlow<Boolean> = _transactionAdded


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

    fun getBalance(userId: Number) {
        viewModelScope.launch {
            try {
                val response = repository.getBalance(userId)
                _balance.value = response
                Log.d("BALANCE", "${_balance.value}")
            }catch (e: Exception) {
                errorMessage = e.message
            }
        }
    }

    fun createTransaction(transactionBody: TransactionBody) {
        viewModelScope.launch {
            try {
                val response = repository.createTransaction(transactionBody)
                _transactionAdded.value = true
            } catch (e: Exception) {
                errorMessage = e.message
            }
        }
    }

    fun createCategory(categoryBody: CategoryBody) {
        viewModelScope.launch {
            try {
                val response = repository.createCategory(categoryBody)
                _categories.value += response

            } catch (e: Exception) {
                errorMessage = e.message
            }
        }
    }

    fun setTransactionAdded(value: Boolean) {
        _transactionAdded.value = value
    }

}