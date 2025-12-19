package com.example.shoppinglist_public.ui.data.local.item

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 4, exportSchema = false)
abstract class ShoppingItemDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDAO
}