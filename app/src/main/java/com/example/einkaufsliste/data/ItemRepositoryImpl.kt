package com.example.einkaufsliste.data

import com.example.einkaufsliste.data.local.item.Item
import com.example.einkaufsliste.data.local.item.ItemDAO
import com.example.einkaufsliste.data.remote.ItemDTO
import com.example.einkaufsliste.data.network.ShoppingListApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val localItemDAO: ItemDAO,
    private val networkDatabase: ShoppingListApiService
) : ItemRepository {

    override fun getAllItemsStream(): Flow<List<Item>> = localItemDAO.getAllItems()

    override fun getItemStream(id: Int): Flow<Item?> = localItemDAO.getItem(id)

    override suspend fun insertItem(item: Item) = localItemDAO.insertItem(item)

    override suspend fun deleteItem(item: Item) = localItemDAO.deleteItem(item)

    override suspend fun updateItem(item: Item) = localItemDAO.updateItem(item)

    override suspend fun getMaxSortOrder(): Int? = localItemDAO.getMaxSortOrder()

    override suspend fun updateSortOrder(itemId: Int, newOrder: Int) =
        localItemDAO.updateSortOrder(itemId, newOrder)

    override fun getAllItemsRemoteStream(): Flow<List<ItemDTO>> = flow {
        val items = networkDatabase.getAllItems()
        emit(items)
    }

    override suspend fun sendRemoteItem(item: ItemDTO): Response<ItemDTO> =
        networkDatabase.sendItem(item)

    override suspend fun removeRemoteItem(item: ItemDTO): Response<ItemDTO> =
        networkDatabase.removeItem(item)

    override suspend fun updateRemoteItem(item: ItemDTO): Response<ItemDTO> =
        networkDatabase.updateItem(item)

}
