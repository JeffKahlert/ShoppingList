package com.example.einkaufsliste.data.remote

import com.example.einkaufsliste.data.local.item.ItemDetails
import java.util.UUID

data class RemoteItemDetails(
    val id: UUID = UUID.randomUUID(),
    val name: String = "", 
    val description: String = "", 
    val isChecked: Int = 0,
)


fun RemoteItemDetails.toRemoteItem(): RemoteItem = RemoteItem(
    id = id,
    name = name,
    description = description,
    isChecked = isChecked,
)