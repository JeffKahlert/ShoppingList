package com.example.einkaufsliste.data.internal.recipe

import com.example.einkaufsliste.data.model.Ingredient
import com.example.einkaufsliste.data.model.Recipe
import com.example.einkaufsliste.data.model.RecipeWithIngredients
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun insertRecipe(recipe: Recipe): Long

    suspend fun insertIngredients(ingredient: Ingredient)

    suspend fun updateRecipe(recipe: Recipe)

    suspend fun deleteRecipe(recipe: Recipe)

    fun getAllRecipes(): Flow<List<RecipeWithIngredients>>

    fun getRecipe(id: Int): Flow<RecipeWithIngredients>
}