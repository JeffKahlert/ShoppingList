package com.example.shoppinglist_public.ui.recipe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.shoppinglist_public.ui.data.local.recipe.RecipeRepository
import com.example.shoppinglist_public.ui.data.local.recipe.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    var addRecipeUiState by mutableStateOf(RecipeAddUiState())
        private set

    var instructionBottomModalUiState by mutableStateOf(InstructionBottomModalUiState())
        private set

    var editInstructionBottomModalUiState by mutableStateOf(EditInstructionBottomModalUiState())
        private set

    var ingredientBottomModalUiState by mutableStateOf(IngredientBottomModalUiState())
        private set

    fun updateUiState(recipeDetails: RecipeDetails) {
        addRecipeUiState =
            RecipeAddUiState(
                recipeDetails = recipeDetails
            )
    }

    fun changeIngredientsBottomModalState(isVisible: Boolean) {
        ingredientBottomModalUiState =
            IngredientBottomModalUiState(isVisible = !isVisible)
    }

    fun changeInstructionBottomModalState(isVisible: Boolean) {
        instructionBottomModalUiState =
            InstructionBottomModalUiState(isVisible = !isVisible)
    }

    fun changeEditInstructionBottomModalState(isVisible: Boolean) {
        editInstructionBottomModalUiState =
            EditInstructionBottomModalUiState(isVisible = !isVisible)
    }

    fun updateInstructionUiState(instructionDetails: InstructionDetails) {
        instructionBottomModalUiState =
            InstructionBottomModalUiState(
                instruction = instructionDetails,
                isVisible = true
            )
    }

    fun updateEditInstructionUiState(recipeDetails: RecipeDetails) {
        editInstructionBottomModalUiState =
            EditInstructionBottomModalUiState(
                recipeDetails = recipeDetails,
                isVisible = true
            )
    }

    fun addIngredient(ingredientDetails: IngredientDetails, ingredients: Set<Map<String, String>>) {
        val ingredientMap: MutableMap<String, String> = mutableMapOf()
        ingredientMap.put(ingredientDetails.ingredient, ingredientDetails.amount)
        val ingredientSet = mutableSetOf<Map<String, String>>()
        ingredientSet.addAll(ingredients)
        ingredientSet.addAll(listOf(ingredientMap))

        ingredientBottomModalUiState =
            IngredientBottomModalUiState(
                ingredients = ingredientSet,
                ingredientDetails = IngredientDetails("", ""),
                isVisible = true
            )
    }

    fun updateIngredientUiState(ingredientDetails: IngredientDetails, ingredients: Set<Map<String, String>>) {
        ingredientBottomModalUiState =
            IngredientBottomModalUiState(
                ingredients = ingredients,
                ingredientDetails = ingredientDetails,
                isVisible = true
            )
    }

    fun transferRecipeUiStateToEditUiState() {
        editInstructionBottomModalUiState = EditInstructionBottomModalUiState(
            recipeDetails =  addRecipeUiState.recipeDetails,
            isVisible = true
        )
    }

    suspend fun saveItem(){
        /*if (validateInput()) {
            val recipe = recipeRepository.insertRecipe(addRecipeUiState.recipeDetails.toRecipe())
            val ingredientList =
                formatIngredientsStringToList(addRecipeUiState.recipeDetails.ingredients)
            val ingredientComponentsList =
            ingredientList.forEach { ingredient ->
                recipeRepository.insertIngredients(
                    Ingredient(
                        name = ingredient,
                        recipeOwnerId = recipe.toInt(),
                        ingredientId = TODO(),
                        amount = TODO(),
                        unit = TODO(),
                    )
                )
            }
        }*/
    }

    private fun validateInput(uiState: RecipeDetails = addRecipeUiState.recipeDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && ingredients.isNotEmpty()
        }
    }

}

data class InstructionBottomModalUiState(
    val isVisible: Boolean = false,
    val instruction: InstructionDetails = InstructionDetails()
)

data class EditInstructionBottomModalUiState(
    val isVisible: Boolean = false,
    val recipeDetails: RecipeDetails = RecipeDetails()
)

data class IngredientBottomModalUiState(
    val isVisible: Boolean = false,
    val ingredients: Set<Map<String, String>> = emptySet(),
    val ingredientDetails: IngredientDetails = IngredientDetails()
)

data class RecipeAddUiState(
    val recipeDetails: RecipeDetails = RecipeDetails(),
)

data class RecipeDetails(
    val id: Int = 0,
    val name: String = "",
    val duration: String = "",
    val amount: String = "",
    val portions: String = "",
    val ingredients: Set<Map<String, String>> = emptySet(),
    val instruction: String = ""
)

data class InstructionDetails(
    val instruction: String = ""
)

data class IngredientDetails(
    val amount: String = "",
    val ingredient: String = ""
)

fun formatIngredientsStringToList(content: String): List<String> {
    val splitIngredients: List<String> = content.split(",")
    val resultList: MutableList<String> = mutableListOf()
    splitIngredients.forEach { ingredient ->
        resultList.add(ingredient.trim())
    }
    return resultList
}


fun RecipeDetails.toRecipe(): Recipe = Recipe(
    recipeId = id,
    name = name,
    instruction = instruction,
)
