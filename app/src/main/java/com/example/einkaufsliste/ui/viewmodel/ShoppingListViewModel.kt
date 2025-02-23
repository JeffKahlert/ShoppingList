package com.example.einkaufsliste.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.einkaufsliste.data.ShoppingListUiState
import com.example.einkaufsliste.model.DumbItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ShoppingListViewModel : ViewModel() {

    private val _shoppingListUiState = MutableStateFlow(ShoppingListUiState())
    val shoppingListUiState: StateFlow<ShoppingListUiState> = _shoppingListUiState.asStateFlow()


    fun onAddClicked(showSheet: Boolean): Boolean {
        return !showSheet ;
    }

    fun addItemsToListClicked(article: String, info: String) {
        _shoppingListUiState.update { currentUiState ->
            if (!currentUiState.items.contains(DumbItem(article, info))) {
                currentUiState.copy(
                    items = currentUiState.items + DumbItem(name = article, description = info)
                )
            } else {
                currentUiState.copy(
                    message = "Artikel mit dieser Info bereits in der Liste vorhanden"
                )
            }
        }
    }

    fun removeItem(currentItem: DumbItem) {
        _shoppingListUiState.update { currentUiState ->
            currentUiState.copy(
                items = currentUiState.items - currentItem
            )
        }
    }

    fun clearMessage() {
        _shoppingListUiState.update { it.copy(message = null) }
    }
}