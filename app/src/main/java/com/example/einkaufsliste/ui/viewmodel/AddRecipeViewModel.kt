package com.example.einkaufsliste.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.einkaufsliste.data.AddRecipeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddRecipeViewModel : ViewModel() {

    private val _addRecipeUiState = MutableStateFlow(AddRecipeUiState())
    val addRecipeUiState: StateFlow<AddRecipeUiState> = _addRecipeUiState.asStateFlow()

    fun updateNameTextFieldValue(name: String) {
        _addRecipeUiState.update { currentUiState ->
            currentUiState.copy(
                name = name
            )
        }
    }

    fun updateIngredientTextFieldValue(content: String) {
        _addRecipeUiState.update { currentUiState ->
            currentUiState.copy(
                ingredient = content
            )
        }
    }

    fun updateInstructionTextFieldValue(content: String?) {
        if (content != null) {
            _addRecipeUiState.update { currentUiState ->
                currentUiState.copy(
                    instruction = content
                )
            }
        }
    }
}