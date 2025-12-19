package com.example.shoppinglist_public.ui.data.local.item

data class ItemDetails(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val isChecked: Int = 0,
    var sortOrderId: Int = 0,
)

fun ItemDetails.toItem(): Item = Item(
    id = id,
    name = name,
    description = description,
    isChecked = isChecked,
)
