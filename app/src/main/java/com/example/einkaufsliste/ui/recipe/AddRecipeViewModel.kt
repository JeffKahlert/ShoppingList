package com.example.einkaufsliste.ui.recipe

import android.util.Log
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

    var instructionBottomModalUiState by mutableStateOf(InstructionBottomModalUiState())
        private set

    var editInstructionBottomModalUiState by mutableStateOf(EditInstructionBottomModalUiState())
        private set


    fun updateUiState(recipeDetails: RecipeDetails) {
        addRecipeUiState =
            RecipeAddUiState(
                recipeDetails = recipeDetails
            )
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


    // Ich mache hier irgendwas falsch, habe aber noch keine Ahnung woran es liegt
    fun transferRecipeUiStateToEditUiState() {
        Log.d("TRANSFER", "Vorher: ${editInstructionBottomModalUiState.recipeDetails.instruction}")

        editInstructionBottomModalUiState = EditInstructionBottomModalUiState(
            recipeDetails =  addRecipeUiState.recipeDetails,
            isVisible = true
        )

        Log.d("TRANSFER", "Nachher: ${editInstructionBottomModalUiState.recipeDetails.instruction}")
    }




    suspend fun saveItem(){
        if (validateInput()) {
            val recipe = recipeRepository.insertRecipe(addRecipeUiState.recipeDetails.toRecipe())
            val ingredientList =
                formatIngredientsStringToList(addRecipeUiState.recipeDetails.ingredients)
            val ingredientComponentsList =
            ingredientList.forEach { ingredient ->
                recipeRepository.insertIngredients(Ingredient(
                    name = ingredient,
                    recipeOwnerId = recipe.toInt(),
                    ingredientId = TODO(),
                    amount = TODO(),
                    unit = TODO(),
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

data class InstructionBottomModalUiState(
    val isVisible: Boolean = false,
    val instruction: InstructionDetails = InstructionDetails()
)

data class EditInstructionBottomModalUiState(
    val isVisible: Boolean = false,
    val recipeDetails: RecipeDetails = RecipeDetails()
)

data class RecipeAddUiState(
    val recipeDetails: RecipeDetails = RecipeDetails(),
)

data class RecipeDetails(
    val id: Int = 0,
    val name: String = "",
    val ingredients: String = "",
    val instruction: String = ""
)

data class InstructionDetails(
    val instruction: String = ""
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
    instruction = instruction
    )
