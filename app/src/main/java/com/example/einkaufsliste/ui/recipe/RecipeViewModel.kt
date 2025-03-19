package com.example.einkaufsliste.ui.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.einkaufsliste.data.internal.recipe.RecipeRepository
import com.example.einkaufsliste.data.model.Recipe
import com.example.einkaufsliste.data.model.RecipeWithIngredients
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    val recipeListUiState: StateFlow<RecipeUiState> =
        recipeRepository.getAllRecipes().map { RecipeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = RecipeUiState()
            )
}

data class RecipeUiState(
    val recipeList: List<RecipeWithIngredients> = emptyList()
)