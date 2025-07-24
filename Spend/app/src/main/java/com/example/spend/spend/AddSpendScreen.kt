package com.example.spend.spend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.spend.auth.AuthViewModel
import com.example.spend.auth.models.Category
import com.example.spend.auth.models.TransactionType
import com.example.spend.spend.models.CategoryBody
import com.example.spend.spend.models.TransactionBody

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSpendScreen(
    authViewModel: AuthViewModel = viewModel(),
    transactionViewModel: TransactionViewModel = viewModel(),
    navController: NavController
) {

    val transactionAdded by transactionViewModel.transactionAdded.collectAsState()

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var newCategoryName by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(0) }
    var type by remember { mutableStateOf("EXPENSE") }
    val categories by transactionViewModel.categories.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheet by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        transactionViewModel.getCategories()
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Créer une Transaction") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Titre
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Nom de la transaction") },
                modifier = Modifier.fillMaxWidth()
            )

            // Montant
            TextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Montant (€)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            CategorySelector(
                categories = categories,
                selectedCategory = category,
                onCategorySelected = { category = it }
            )
            TextButton(onClick = {showSheet = true}) {
                Text("Créer une category")
            }
            //category.toString(),
            // Type de transaction
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



            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val parsedAmount = amount.toDoubleOrNull() ?: 0.0
                    transactionViewModel.createTransaction(TransactionBody(
                        title = title,
                        amount = parsedAmount,
                        categoryId = category.toInt(),
                        userId = authViewModel.user.value!!.id,
                        type = TransactionType.valueOf(type)
                    ))
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotBlank() && amount.toDoubleOrNull() != null
            ) {
                Text("Enregistrer")
            }
            LaunchedEffect(transactionAdded) {
                    // Affiche le snackbar
                if(transactionAdded) {
                    val result = snackbarHostState.showSnackbar("Transaction ajoutée avec succès !")

                    // Après dismissal ou timeout, navigue
                    navController.navigate("home") {
                        popUpTo("addTransaction") { inclusive = true }
                    }
                    transactionViewModel.setTransactionAdded(false)
                }
            }
            if (showSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showSheet = false },
                    sheetState = sheetState
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text("Ajouter une categorie", style = MaterialTheme.typography.titleMedium)
                        TextField(
                            value = newCategoryName,
                            onValueChange = { newCategoryName = it },
                            label = { Text("Nom de la categorie") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(
                            onClick = {
                                val test = transactionViewModel.createCategory(
                                    CategoryBody(newCategoryName,authViewModel.user.value!!.id)

                                )
                                showSheet = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Valider")
                        }
                        OutlinedButton (
                            onClick = {
                                // Utilise name ici (ex: enregistrer)
                                showSheet = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Annuler")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelector(
    categories: List<Category>,
    selectedCategory: Int,
    onCategorySelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = if(selectedCategory == 0) {"Choisissez une categorie"} else {categories.find { x -> x.id == selectedCategory }!!.name},
            onValueChange = {},
            readOnly = true,
            label = { Text("Catégorie") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.name) },
                    onClick = {
                        onCategorySelected(category.id)
                        expanded = false
                    }
                )
            }
        }
    }
}

