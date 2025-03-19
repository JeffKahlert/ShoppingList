package com.example.einkaufsliste.ui.recipe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.einkaufsliste.data.internal.recipe.RecipeRepository
import com.example.einkaufsliste.data.model.Ingredient
import com.example.einkaufsliste.data.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    var addRecipeUiState by mutableStateOf(RecipeAddUiState())
        private set

    fun updateUiSate(recipeDetails: RecipeDetails) {
        addRecipeUiState =
            RecipeAddUiState(
                recipeDetails = recipeDetails
            )
    }

    suspend fun saveItem(){
        if (validateInput()) {
            val recipe = recipeRepository.insertRecipe(addRecipeUiState.recipeDetails.toRecipe())
            val ingredientList =
                formatIngredientsStringToList(addRecipeUiState.recipeDetails.ingredients)

            ingredientList.forEach { ingredient ->
                recipeRepository.insertIngredients(Ingredient(
                    name = ingredient,
                    recipeOwnerId = recipe.toInt(),
                ))
            }
        }
    }

    private fun validateInput(uiState: RecipeDetails = addRecipeUiState.recipeDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && ingredients.isNotBlank()
        }
    }

}

data class RecipeAddUiState(
    val recipeDetails: RecipeDetails = RecipeDetails(),
)

data class RecipeDetails(
    val id: Int = 0,
    val name: String = "",
    val ingredients: String = "",
    val instruction: String = ""
)

fun formatIngredientsStringToList(content: String): List<String> {
    val splitString: List<String> = content.split(",")
    val resultList: MutableList<String> = mutableListOf()
    splitString.forEach { ingredient ->
        resultList.add(ingredient.trim())
    }
    return resultList
}

fun RecipeDetails.toRecipe(): Recipe = Recipe(
    recipeId = id,
    name = name,
    instruction = instruction
    )
