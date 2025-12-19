package com.example.shoppinglist_public.ui.shoppinglist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist_public.ui.data.ItemRepository
import com.example.shoppinglist_public.ui.data.local.item.Item
import com.example.shoppinglist_public.ui.data.local.item.ItemDetails
import com.example.shoppinglist_public.ui.data.local.item.toItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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

    suspend fun removeItem(item: Item) {
        itemRepository.deleteItem(item)
    }

    fun updateBottomSheetUiState(itemDetails: ItemDetails) {
        Log.i("Eingabe", "TEST")
        bottomSheetUiState =
            BottomSheetUiState(
                itemDetails = itemDetails,
                isBottomSheetVisible = true,
                isEntryValid = validateInput(itemDetails)
            )
    }

    suspend fun updateItem(item: Item){
        itemRepository.updateItem(item)
    }

    fun saveItem() {
        viewModelScope.launch {
            if (validateInput()) {
                val item = bottomSheetUiState.itemDetails.toItem()
                itemRepository.insertItem(item)
                /*val maxOrder = itemRepository.getMaxSortOrder() ?: 0
                bottomSheetUiState.itemDetails.sortOrderId = maxOrder + 1*/
            }
        }
    }

    /*fun sendItems() {
        Log.i("PRESSED", "SEND ITEMS GEDRUECKT")
        viewModelScope.launch {
            try {
                Log.i("INSIDE", "SEND ITEMS INSIDE")
                val items = itemRepository.getAllItemsStream().first()
                val dbItems = items.map { it.toDto() }

                itemRepository.sendItem(dbItems)
            } catch(ex: Exception) {
                Log.i("ERROR", "Error beim senden: $ex")
            }
        }
    }*/

    private fun validateInput(uiState: ItemDetails = bottomSheetUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }

    // Try to add Drag-n-Drop
    /*suspend fun moveItem(from: Int, to: Int) {
        val currentItems = shoppingListUiState.value.items
        val mutableList = currentItems.toMutableList()

        val item = mutableList.removeAt(from)
        mutableList.add(to, item)

        mutableList.forEachIndexed  { index, item ->
            itemRepository.updateSortOrder(item.id, index)
        }
    }*/
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
)