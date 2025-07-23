package com.example.spend.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.spend.auth.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

     var isLogged by mutableStateOf<Boolean>(false)

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user


    private var errorMessage by mutableStateOf<String?>(null)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                _user.value = User(response.id,response.email)
                errorMessage = null
                isLogged = true
            } catch (e: Exception) {
                errorMessage = e.message
            }
        }
    }

    fun register(email: String, password: String) {
//        viewModelScope.launch {
//            try {
//                val response = repository.register(email,password)
//                user = User(
//                    id = response.data.id,
//                    email = response.data.email,
//                )
//                errorMessage = null
//                isLogged = true
//            } catch (e: Exception) {
//                errorMessage = e.message
//            }
//        }
    }

    fun updateUser(email: String, password: String, userId: Int) {
        viewModelScope.launch {
            user.value?.let {
                val response = repository.updateUser(email,password, it.id)
                _user.value = User(
                    id = response.id,
                    email = response.email,
                )
            }
        }

    }
}