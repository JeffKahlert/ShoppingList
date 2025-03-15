package com.example.einkaufsliste.ui.shoppinglist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.einkaufsliste.data.internal.ItemRepository
import com.example.einkaufsliste.data.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {

    var bottomSheetUiState by mutableStateOf(BottomSheetUiState())
        private set


    val shoppingListUiState: StateFlow<ShoppingListUiState> =
        itemRepository.getAllItemsStream().map { ShoppingListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = ShoppingListUiState()
            )

    fun changeBottomSheetUiState(sheetState: Boolean) {
        bottomSheetUiState =
            BottomSheetUiState(isBottomSheetVisible = !sheetState)
    }

    /*fun removeItem(currentItem: Item) {
       bottomSheetUiState =
           bottomSheetUiState(items = bottomSheetUiState.items - currentItem)
   }*/

    suspend fun removeItem(item: Item) {
        Log.e("DELETEITEM", item.id.toString())
        itemRepository.deleteItem(item)
    }

    fun updateBottomSheetUiState(itemDetails: ItemDetails) {
        bottomSheetUiState =
            BottomSheetUiState(
                itemDetails = itemDetails,
                isBottomSheetVisible = true,
                isEntryValid = validateInput(itemDetails)
            )
    }


    suspend fun saveItem() {
        if (validateInput()) {
            itemRepository.insertItem(bottomSheetUiState.itemDetails.toItem())
        }
    }

    private fun validateInput(uiState: ItemDetails = bottomSheetUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }

}

data class BottomSheetUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isBottomSheetVisible: Boolean = false,
    val isEntryValid: Boolean = false
)


data class ShoppingListUiState(
    val items: List<Item> = emptyList(),
    val checkedItems: List<Item> = emptyList(),
    val message: String? = null,
    val itemToDelete: ItemDetails = ItemDetails()
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