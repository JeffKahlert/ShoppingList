package com.example.shoppinglist_public.ui.data.local.recipe

import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun insertRecipe(recipe: Recipe): Long

    suspend fun insertIngredients(ingredient: Ingredient)

    suspend fun updateRecipe(recipe: Recipe)

    suspend fun deleteRecipe(recipe: Recipe)

    fun getAllRecipes(): Flow<List<RecipeWithIngredients>>

    fun getRecipe(id: Int): Flow<RecipeWithIngredients>
}