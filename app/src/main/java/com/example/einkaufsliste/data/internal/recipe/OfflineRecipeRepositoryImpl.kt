package com.example.einkaufsliste.data.internal.recipe

import com.example.einkaufsliste.data.model.Ingredient
import com.example.einkaufsliste.data.model.Recipe
import com.example.einkaufsliste.data.model.RecipeWithIngredients
import kotlinx.coroutines.flow.Flow

class OfflineRecipeRepositoryImpl(private val recipeDao: RecipeDAO) : RecipeRepository {

    override suspend fun insertRecipe(recipe: Recipe): Long =
        recipeDao.insertRecipe(recipe)

    override suspend fun insertIngredients(ingredient: Ingredient) =
        recipeDao.insertIngredients(ingredient)

    override suspend fun updateRecipe(recipe: Recipe) =
        recipeDao.updateRecipe(recipe)

    override suspend fun deleteRecipe(recipe: Recipe) =
        recipeDao.deleteRecipe(recipe)

    override fun getAllRecipes(): Flow<List<RecipeWithIngredients>> =
        recipeDao.getAllRecipes()

    override fun getRecipe(id: Int): Flow<RecipeWithIngredients> =
        recipeDao.getRecipe(id)
}