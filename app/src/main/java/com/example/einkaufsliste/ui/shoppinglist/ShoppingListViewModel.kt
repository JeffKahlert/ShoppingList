package com.example.einkaufsliste.ui.shoppinglist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.einkaufsliste.data.internal.ItemRepository
import com.example.einkaufsliste.data.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val itemRepository: ItemRepository
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
        Log.e("TAG", "IN DER METHODE updateBottomSheetUiState")
        shoppingListUiState =
            shoppingListUiState.copy(sheetItem = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    suspend fun saveItem() {
        itemRepository.insertItem(shoppingListUiState.itemDetails.toItem())
    }

    private fun validateInput(uiState: ItemDetails = shoppingListUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }

    /*fun clearMessage() {
        shoppingListUiState.update { it.copy(message = null) }
    }*/
}

data class ShoppingListUiState(
    val items: List<Item> = emptyList(),
    val checkedItems: List<Item> = emptyList(),
    val message: String? = null,
    val itemDetails: ItemDetails = ItemDetails(),
    val sheetItem: ItemDetails = ItemDetails(),
    val isBottomSheetVisible: Boolean = false,
    val isEntryValid: Boolean = false
)


data class ItemDetails(
    val id: Int = 0,
    val name: String = "",
    val description: String = ""
)

fun Item.toItemDetails(): ItemDetails = ItemDetails(
    name = name,
    description = description
)

fun ItemDetails.toItem(): Item = Item(
    id = id,
    name = name,
    description = description,
)