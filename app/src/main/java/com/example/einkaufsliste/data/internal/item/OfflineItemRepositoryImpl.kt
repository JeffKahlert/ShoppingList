package com.example.einkaufsliste.data.internal.item

import com.example.einkaufsliste.data.model.Item
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineItemRepositoryImpl @Inject constructor(
    private val itemDAO: ItemDAO
) : ItemRepository {

    override fun getAllItemsStream(): Flow<List<Item>> = itemDAO.getAllItems()

    override fun getItemStream(id: Int): Flow<Item?> = itemDAO.getItem(id)

    override suspend fun insertItem(item: Item) = itemDAO.insertItem(item)

    override suspend fun deleteItem(item: Item) = itemDAO.deleteItem(item)

    override suspend fun updateItem(item: Item) = itemDAO.updateItem(item)

    override suspend fun getMaxSortOrder(): Int? = itemDAO.getMaxSortOrder()

    override suspend fun updateSortOrder(itemId: Int, newOrder: Int) =
        itemDAO.updateSortOrder(itemId, newOrder)
}