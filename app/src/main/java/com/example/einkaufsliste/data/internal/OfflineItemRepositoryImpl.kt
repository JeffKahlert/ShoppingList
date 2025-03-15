package com.example.einkaufsliste.data.internal

import com.example.einkaufsliste.data.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class OfflineItemRepositoryImpl @Inject constructor(
    private val itemDAO: ItemDAO
) : ItemRepository {

    override fun getAllItemsStream(): Flow<List<Item>> = itemDAO.getAllItems()

    override fun getItemStream(id: Int): Flow<Item?> = emptyFlow()

    override suspend fun insertItem(item: Item) = itemDAO.insertItem(item)

    override suspend fun deleteItem(item: Item) = itemDAO.deleteItem(item)

    override suspend fun updateItem(item: Item) = itemDAO.updateItem(item)
}