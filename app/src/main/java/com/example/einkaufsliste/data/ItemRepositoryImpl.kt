package com.example.einkaufsliste.data

import com.example.einkaufsliste.data.local.item.Item
import com.example.einkaufsliste.data.local.item.ItemDAO
import com.example.einkaufsliste.data.remote.ItemDTO
import com.example.einkaufsliste.data.network.ShoppingListApiService
import kotlinx.coroutines.flow.Flow
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

    override suspend fun sendAllItems(items: ItemDTO): Response<ItemDTO> {
        return networkDatabase.sendAllItems(items)
    }
}
