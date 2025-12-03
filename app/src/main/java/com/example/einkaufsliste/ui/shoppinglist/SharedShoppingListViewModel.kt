package com.example.einkaufsliste.ui.shoppinglist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.einkaufsliste.data.ItemRepository
import com.example.einkaufsliste.data.local.item.Item
import com.example.einkaufsliste.data.local.item.ItemDetails
import com.example.einkaufsliste.data.remote.RemoteItem
import com.example.einkaufsliste.data.remote.toDto
import com.example.einkaufsliste.data.local.item.toItem
import com.example.einkaufsliste.data.remote.RemoteItemDetails
import com.example.einkaufsliste.data.remote.toRemoteItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedShoppingListViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {

    var sharedListBottomSheetUiState by mutableStateOf(SharedListBottomSheetUiState())
        private set

    var sharedShoppingItemListUiState: StateFlow<SharedItemListUiState> =
        itemRepository.getAllItemsRemoteStream().map { dtoList ->
            SharedItemListUiState(
                items = dtoList.map { it.toRemoteItem() }
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = SharedItemListUiState()
            )

    fun changeSharedBottomSheetUiState(sheetState: Boolean) {
        sharedListBottomSheetUiState =
            SharedListBottomSheetUiState(isBottomSheetVisible = !sheetState)
    }

    fun updateSharedBottomSheetUiState(itemDetails: RemoteItemDetails) {
        Log.i("EINGABE", "EINGABE TEST ")
        sharedListBottomSheetUiState =
            SharedListBottomSheetUiState(
                itemDetails = itemDetails,
                isBottomSheetVisible = true,
                isEntryValid = validateInput(itemDetails)
            )
    }

    private fun validateInput(
        uiState: RemoteItemDetails = sharedListBottomSheetUiState.itemDetails
    ): Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }

    suspend fun removeRemoteItem(item: RemoteItem) {
        val itemDTO = item.toDto()
        itemRepository.removeRemoteItem(itemDTO)
    }

    fun sendRemoteItem() {
        viewModelScope.launch {
            if (validateInput()) {
                val itemDto = sharedListBottomSheetUiState.itemDetails.toRemoteItem().toDto()
                itemRepository.sendRemoteItem(itemDto)
            }
        }
    }

    suspend fun updateRemoteItem(item: RemoteItem) {
        val itemDTO = item.toDto()
        itemRepository.updateRemoteItem(itemDTO)
    }
}

data class SharedItemListUiState(
    val items: List<RemoteItem> = emptyList()
)

data class SharedListBottomSheetUiState(
    val itemDetails: RemoteItemDetails = RemoteItemDetails(),
    val isBottomSheetVisible: Boolean = false,
    val isEntryValid: Boolean = false
)