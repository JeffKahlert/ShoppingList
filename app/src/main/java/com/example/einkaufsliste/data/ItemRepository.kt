package com.example.einkaufsliste.data

import com.example.einkaufsliste.data.local.item.Item
import com.example.einkaufsliste.data.remote.ItemDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

/**
 * Repository that provides insert, update, delete, and retrieve of [com.example.einkaufsliste.data.local.item.Item] from a given data source.
 */
interface ItemRepository {

    fun getAllItemsStream(): Flow<List<Item>>

    fun getItemStream(id: Int): Flow<Item?>

    suspend fun insertItem(item: Item)

    suspend fun deleteItem(item: Item)

    suspend fun updateItem(item: Item)

    suspend fun getMaxSortOrder(): Int?

    suspend fun updateSortOrder(itemId: Int, newOrder: Int)

    suspend fun sendAllItems(items: ItemDTO): Response<ItemDTO>
}