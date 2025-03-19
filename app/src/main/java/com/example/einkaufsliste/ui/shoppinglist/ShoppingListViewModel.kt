package com.example.einkaufsliste.ui.shoppinglist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.einkaufsliste.data.internal.item.ItemRepository
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

    suspend fun removeItem(item: Item) {
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

    suspend fun updateItem(item: Item){
        itemRepository.updateItem(item)
    }

    suspend fun saveItem() {
        if (validateInput()) {
            /*val maxOrder = itemRepository.getMaxSortOrder() ?: 0
            bottomSheetUiState.itemDetails.sortOrderId = maxOrder + 1*/
            itemRepository.insertItem(
                bottomSheetUiState.itemDetails.toItem()
            )
        }
    }

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

data class ItemDetails(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val isChecked: Int = 0,
    var sortOrderId: Int = 0,
)

fun Item.toItemDetails(): ItemDetails = ItemDetails(
    name = name,
    description = description
)

fun ItemDetails.toItem(): Item = Item(
    id = id,
    name = name,
    description = description,
    isChecked = isChecked,
)
