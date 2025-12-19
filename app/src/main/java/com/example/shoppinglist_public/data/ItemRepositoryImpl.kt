package com.example.shoppinglist_public.ui.data

import com.example.shoppinglist_public.ui.data.local.item.Item
import com.example.shoppinglist_public.ui.data.local.item.ItemDAO
import com.example.shoppinglist_public.ui.data.remote.ItemDTO
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.result.PostgrestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val localItemDAO: ItemDAO,
    // private val networkDatabase: ShoppingListApiService,
    private val supabase: SupabaseClient,
) : ItemRepository {

    override fun getAllItemsStream(): Flow<List<Item>> = localItemDAO.getAllItems()

    override fun getItemStream(id: Int): Flow<Item?> = localItemDAO.getItem(id)

    override suspend fun insertItem(item: Item) = localItemDAO.insertItem(item)

    override suspend fun deleteItem(item: Item) = localItemDAO.deleteItem(item)

    override suspend fun updateItem(item: Item) = localItemDAO.updateItem(item)

    override suspend fun getMaxSortOrder(): Int? = localItemDAO.getMaxSortOrder()

    override suspend fun updateSortOrder(itemId: Int, newOrder: Int) =
        localItemDAO.updateSortOrder(itemId, newOrder)

    private val refreshTrigger = MutableSharedFlow<Unit>()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllItemsRemoteStream(): Flow<List<ItemDTO>> =
        refreshTrigger.onStart { emit(Unit) }.flatMapLatest {
            flow {
                val result = supabase.from("Einkaufsliste")
                    .select()
                    .decodeList<ItemDTO>()
                emit(result)
            }.flowOn(Dispatchers.IO)
        }

    override suspend fun refreshItems() {
        refreshTrigger.emit(Unit)
    }

    override suspend fun sendRemoteItem(item: ItemDTO): PostgrestResult {
        return supabase.from("Einkaufsliste")
            .insert(item)
    }

    override suspend fun removeRemoteItem(item: ItemDTO): PostgrestResult {
        return supabase.from("Einkaufsliste")
            .delete {
                filter {
                    ItemDTO::id eq item.id
                }
            }
    }

    override suspend fun updateRemoteItem(
        item: ItemDTO
    ): PostgrestResult {
        return supabase.from("Einkaufsliste")
            .update(
                {
                    ItemDTO::checked setTo item.checked
                }
            ) {
                filter {
                    ItemDTO::id eq item.id.toString()
                }
            }
    }






    // Ursprüngliche Implementierung der Methoden für das Spring Boot Backend
    /*override fun getAllItemsRemoteStream(): Flow<List<ItemDTO>> = flow {
        val items = networkDatabase.getAllItems()
        emit(items)
    }*/

    /*private val _itemsFlow = MutableStateFlow<List<ItemDTO>>(emptyList())

    override fun getAllItemsRemoteStream(): Flow<List<ItemDTO>> = _itemsFlow.asStateFlow()

    override suspend fun sendRemoteItem(item: ItemDTO): Response<ItemDTO> {
        val response = networkDatabase.sendItem(item)
        if (response.isSuccessful) {
            refreshItems()
        }
        return response
    }

    override suspend fun removeRemoteItem(item: ItemDTO): Response<ItemDTO> {
        val response = networkDatabase.removeItem(item.id.toString())
        if (response.isSuccessful) {
            refreshItems()
        }
        return response
    }

    override suspend fun updateRemoteItem(uuid: String, item: ItemDTO): Response<ItemDTO> {
        val response = networkDatabase.updateItem(uuid, item)
        if (response.isSuccessful) {
            refreshItems()
        }
        return response
    }

    override suspend fun refreshItems() {  // ✅ Override
        try {
            val items = networkDatabase.getAllItems()
            _itemsFlow.value = items
        } catch (e: Exception) {
            _itemsFlow.value = emptyList()
        }
    }*/

}
