package com.example.einkaufsliste.data.internal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.einkaufsliste.data.model.Item

@Database(entities = [Item::class], version = 2, exportSchema = false)
abstract class ShoppingItemDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDAO
}