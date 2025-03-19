package com.example.einkaufsliste.data.internal.recipe

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.einkaufsliste.data.model.Ingredient
import com.example.einkaufsliste.data.model.Recipe

@Database(entities = [Recipe::class, Ingredient::class], version = 4, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDAO
}