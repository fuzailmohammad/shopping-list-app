package com.example.shopping_list_app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingListScreen() {
    var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }
    var editingItemId by remember { mutableStateOf<Int?>(null) }

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
                ShoppingItemRow(item = it, onEdit = {
                    itemName = it.name
                    itemQuantity = it.quantity.toString()
                    editingItemId = it.id
                    showDialog = true
                }, onDelete = {
                    sItems = sItems.filter { item -> item.id != it.id }
                })
            }
        }
    }
    if (showDialog) {
        ShoppingItemDialog(
            itemName = itemName,
            onNameChange = { itemName = it },
            itemQuantity = itemQuantity,
            onQuantityChange = { itemQuantity = it },
            onConfirm = {
                showDialog = false
                if (editingItemId != null) {
                    sItems = sItems.map {
                        if (it.id == editingItemId) it.copy(
                            name = itemName,
                            quantity = itemQuantity.toIntOrNull() ?: 1
                        )
                        else it
                    }
                } else {
                    sItems = sItems + ShoppingItem(
                        id = (sItems.maxOfOrNull { it.id } ?: 0) + 1,
                        itemName,
                        itemQuantity.toIntOrNull() ?: 1
                    )
                }
                editingItemId = null
            },
            onDismiss = {
                showDialog = false
                editingItemId = null
            }
        )
    }
}
