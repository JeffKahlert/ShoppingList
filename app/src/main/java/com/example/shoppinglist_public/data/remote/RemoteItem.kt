package com.example.shoppinglist_public.ui.data.remote

import java.util.UUID

data class RemoteItem(
    val id: UUID,
    val name: String,
    val description: String,
    val checked: Boolean,
)

fun RemoteItem.toDto(): ItemDTO = ItemDTO(
    id = id,
    name = name,
    description = description,
    checked = checked,
)
