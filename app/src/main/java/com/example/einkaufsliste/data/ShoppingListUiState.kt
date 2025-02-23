package com.example.einkaufsliste.data

import com.example.einkaufsliste.model.DumbItem

data class ShoppingListUiState(
    val items: List<DumbItem> = emptyList(),
    val checkedItems: List<DumbItem> = emptyList(),
    val message: String? = null
)
