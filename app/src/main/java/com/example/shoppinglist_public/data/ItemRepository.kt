package com.example.shoppinglist_public.ui.data

import com.example.shoppinglist_public.ui.data.local.item.Item
import com.example.shoppinglist_public.ui.data.remote.ItemDTO
import io.github.jan.supabase.postgrest.result.PostgrestResult
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Item] from a given data source.
 */
interface ItemRepository {

    fun getAllItemsStream(): Flow<List<Item>>

    fun getItemStream(id: Int): Flow<Item?>

    suspend fun insertItem(item: Item)

    suspend fun deleteItem(item: Item)

    suspend fun updateItem(item: Item)

    suspend fun getMaxSortOrder(): Int?

    suspend fun updateSortOrder(itemId: Int, newOrder: Int)

    fun getAllItemsRemoteStream(): Flow<List<ItemDTO>>

    suspend fun sendRemoteItem(item: ItemDTO): PostgrestResult

    suspend fun removeRemoteItem(item: ItemDTO): PostgrestResult

    suspend fun updateRemoteItem(item: ItemDTO): PostgrestResult

    suspend fun refreshItems()

    // Ursprüngliche Implementierung der Methoden für das Spring Boot Backend
    /*suspend fun getAllItemsRemoteStream(): Flow<List<ItemDTO>>

    suspend fun sendRemoteItem(item: ItemDTO): Response<ItemDTO>

    suspend fun removeRemoteItem(item: ItemDTO): Response<ItemDTO>

    suspend fun updateRemoteItem(uuid: String, item: ItemDTO): Response<ItemDTO>

    suspend fun refreshItems()*/
}