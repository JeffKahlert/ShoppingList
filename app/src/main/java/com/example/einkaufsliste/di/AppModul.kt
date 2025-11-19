package com.example.einkaufsliste.di

import android.content.Context
import androidx.room.Room
import com.example.einkaufsliste.data.ItemRepository
import com.example.einkaufsliste.data.ItemRepositoryImpl
import com.example.einkaufsliste.data.local.item.ItemDAO
import com.example.einkaufsliste.data.local.item.ShoppingItemDatabase
import com.example.einkaufsliste.data.local.recipe.OfflineRecipeRepositoryImpl
import com.example.einkaufsliste.data.local.recipe.RecipeDAO
import com.example.einkaufsliste.data.local.recipe.RecipeDatabase
import com.example.einkaufsliste.data.local.recipe.RecipeRepository
import com.example.einkaufsliste.data.network.ShoppingListApiService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    fun provideItemDao(database: ShoppingItemDatabase): ItemDAO {
        return database.itemDao()
    }

    @Provides
    @Singleton
    fun provideItemRepository(itemDAO: ItemDAO, network: ShoppingListApiService): ItemRepository {
        return ItemRepositoryImpl(itemDAO, network)
    }

    @Provides
    @Singleton
    fun provideRecipeDatabase(@ApplicationContext context: Context): RecipeDatabase {
        return Room.databaseBuilder(
            context,
            RecipeDatabase::class.java,
            "recipe_database")
            .fallbackToDestructiveMigration(false)
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


    // Für den späteren Einsatz von Auth
    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder().build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ) : Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            //.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideShoppingListApiService(retrofit: Retrofit): ShoppingListApiService {
        return retrofit.create(ShoppingListApiService::class.java)
    }
}