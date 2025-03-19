package com.example.einkaufsliste.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    @ColumnInfo(name = "isChecked", defaultValue = "0")
    val isChecked: Int = 0,
    @ColumnInfo(name = "sortOrderId", defaultValue = "0")
    val sortOrderId: Int = 0,
)

