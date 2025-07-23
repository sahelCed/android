package com.example.spend.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spend.auth.AuthViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ProfileContent(
    authViewModel: AuthViewModel = viewModel(),
    navController: NavController
){
    val user by authViewModel.user.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    // Champs modifiables
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("default") }


    LaunchedEffect(user) {
        user?.let {
            email = it.email
        }
    }

    Column (modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Modifier mes informations", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                user?.let {
                    authViewModel.updateUser(email, password, it.id)
                    keyboardController?.hide()
                }
            },
            enabled = password.isNotBlank() && email.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enregistrer")
        }
    }

}