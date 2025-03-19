package com.example.einkaufsliste.data.internal.item

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.einkaufsliste.data.model.Item

@Database(entities = [Item::class], version = 4, exportSchema = false)
abstract class ShoppingItemDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDAO
}