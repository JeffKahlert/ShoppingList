package com.example.shoppinglist_public.ui.data.local.item

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDAO {

    @Insert
    suspend fun insertItem(item: Item)

    @Update
    suspend fun updateItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<Item>>

    @Query("SELECT * FROM items WHERE id = :id")
    fun getItem(id : Int): Flow<Item>

    @Query("SELECT MAX(sortOrderId) FROM items")
    suspend fun getMaxSortOrder(): Int?

    @Query("UPDATE items SET sortOrderId = :newOrder WHERE id = :itemId")
    suspend fun updateSortOrder(itemId: Int, newOrder: Int)
}