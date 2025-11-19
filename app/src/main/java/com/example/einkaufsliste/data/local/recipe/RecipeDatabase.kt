package com.example.einkaufsliste.data.local.recipe

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Recipe::class, Ingredient::class], version = 5, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDAO
}