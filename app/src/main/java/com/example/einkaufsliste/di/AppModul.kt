package com.example.einkaufsliste.di

import android.content.Context
import androidx.room.Room
import com.example.einkaufsliste.data.internal.item.ItemDAO
import com.example.einkaufsliste.data.internal.item.ItemRepository
import com.example.einkaufsliste.data.internal.item.OfflineItemRepositoryImpl
import com.example.einkaufsliste.data.internal.item.ShoppingItemDatabase
import com.example.einkaufsliste.data.internal.recipe.OfflineRecipeRepositoryImpl
import com.example.einkaufsliste.data.internal.recipe.RecipeDAO
import com.example.einkaufsliste.data.internal.recipe.RecipeDatabase
import com.example.einkaufsliste.data.internal.recipe.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ShoppingItemDatabase {
        return Room.databaseBuilder(
            context,
            ShoppingItemDatabase::class.java,
            "item_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideItemDao(database: ShoppingItemDatabase): ItemDAO {
        return database.itemDao()
    }

    @Provides
    @Singleton
    fun provideItemRepository(itemDAO: ItemDAO): ItemRepository {
        return OfflineItemRepositoryImpl(itemDAO)
    }

    @Provides
    @Singleton
    fun provideRecipeDatabase(@ApplicationContext context: Context): RecipeDatabase {
        return Room.databaseBuilder(
            context,
            RecipeDatabase::class.java,
            "recipe_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideRecipeDao(database: RecipeDatabase): RecipeDAO {
        return database.recipeDao()
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(recipeDAO: RecipeDAO): RecipeRepository {
        return OfflineRecipeRepositoryImpl(recipeDAO)
    }

}