package com.example.shopping_list_app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingListScreen() {
    var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            onClick = {
                itemName = ""
                itemQuantity = ""
                showDialog = !showDialog
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        ) {
            Text("Add Shopping Item")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            items(sItems) {
                // Display each shopping item
                Text(
                    text = "${it.name} (Quantity: ${it.quantity})",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
    if (showDialog) {
        // Show dialog for adding/editing shopping item
        // This part is not implemented in this snippet, but you can use a Dialog composable
        // to show a form for adding or editing a shopping item.
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    "Add/Edit Shopping Item",
                    textAlign = TextAlign.Center
                )
            },
            text = {
                // Here you can add TextFields for item name and quantity
                Column {
                    Text(
                        "This is where you would add or edit a shopping item.",
                        textAlign = TextAlign.Center
                    )
                    OutlinedTextField(
                        value = itemName,
                        onValueChange = { itemName = it },
                        label = { Text("Item Name") },
                        modifier = Modifier.padding(bottom = 8.dp),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            keyboardType = KeyboardType.Text,
                        ),
                    )
                    OutlinedTextField(
                        value = itemQuantity,
                        onValueChange = { itemQuantity = it },
                        label = { Text("Item Quantity") },
                        modifier = Modifier.padding(bottom = 8.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                        ),
                        singleLine = true,
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    showDialog = false; sItems = sItems +
                        ShoppingItem(
                            id = sItems.size + 1, // Simple ID generation
                            itemName,
                            itemQuantity.toIntOrNull() ?: 1, // Default to 1 if parsing fails
                        )

                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Dismiss")
                }
            }
        )
    }
}
