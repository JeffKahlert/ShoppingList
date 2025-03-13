package com.example.einkaufsliste.data.internal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.einkaufsliste.data.model.Item

@Dao
interface ItemDAO {

    @Insert
    suspend fun insertItem(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

}