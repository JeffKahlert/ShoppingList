package com.example.shoppinglist_public.ui.data.remote

import java.util.UUID

data class RemoteItemDetails(
    val id: UUID = UUID.randomUUID(),
    val name: String = "", 
    val description: String = "", 
    val checked: Boolean = false,
)


fun RemoteItemDetails.toRemoteItem(): RemoteItem = RemoteItem(
    id = id,
    name = name,
    description = description,
    checked = checked,
)