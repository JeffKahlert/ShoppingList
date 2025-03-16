package com.example.einkaufsliste.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.einkaufsliste.data.internal.ItemDAO
import com.example.einkaufsliste.data.internal.ItemRepository
import com.example.einkaufsliste.data.internal.OfflineItemRepositoryImpl
import com.example.einkaufsliste.data.internal.ShoppingItemDatabase
import com.example.einkaufsliste.data.model.Item
import dagger.Binds
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

}