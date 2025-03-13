package com.example.einkaufsliste.data.internal

import com.example.einkaufsliste.data.model.Item
import kotlinx.coroutines.flow.Flow

class OfflineItemRepository : ItemRepository {
    override fun getAllItemsStream(): Flow<List<Item>> {
        TODO("Not yet implemented")
    }

    override fun getItemStream(id: Int): Flow<Item?> {
        TODO("Not yet implemented")
    }

    override suspend fun insertItem(item: Item) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItem(item: Item) {
        TODO("Not yet implemented")
    }

    override suspend fun updateItem(item: Item) {
        TODO("Not yet implemented")
    }
}