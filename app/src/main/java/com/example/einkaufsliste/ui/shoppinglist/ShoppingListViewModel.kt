package com.example.einkaufsliste.ui.shoppinglist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.einkaufsliste.data.model.Item
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class ShoppingListViewModel @Inject constructor(

) : ViewModel() {

    var shoppingListUiState by mutableStateOf(ShoppingListUiState())
        private set

    fun onAddClicked(showSheet: Boolean): Boolean {
        return !showSheet ;
    }

    fun changeBottomSheetUiState(sheetState: Boolean) {
        shoppingListUiState =
            ShoppingListUiState(isBottomSheetVisible = !sheetState)
    }

    fun addItemUpdateUiState(itemDetails: ItemDetails) {
        shoppingListUiState =
            ShoppingListUiState(
                items = shoppingListUiState.items + Item(
                    name = itemDetails.name, description = itemDetails.description
                )
            )
    }

    fun removeItem(currentItem: Item) {
       shoppingListUiState =
           ShoppingListUiState(items = shoppingListUiState.items - currentItem)

   }

    fun updateBottomSheetUiState(itemDetails: ItemDetails) {
        shoppingListUiState =
            ShoppingListUiState(sheetItem = Item(itemDetails.name, itemDetails.description))
    }

    /*fun clearMessage() {
        shoppingListUiState.update { it.copy(message = null) }
    }*/
}

data class ShoppingListUiState(
    val items: List<Item> = emptyList(),
    val checkedItems: List<Item> = emptyList(),
    val message: String? = null,
    val sheetItem: Item = Item("", ""),
    val isBottomSheetVisible: Boolean = false
)


data class ItemDetails(
    val name: String = "",
    val description: String = ""
)

fun Item.toItemDetails(): ItemDetails = ItemDetails(
    name = name,
    description = description
)