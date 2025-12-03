package com.example.einkaufsliste.data.remote

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class ItemDTO(
    @SerializedName("id")
    val id: UUID = UUID.randomUUID(),
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("isChecked")
    val isChecked: Int = 0
)

fun ItemDTO.toRemoteItem(): RemoteItem = RemoteItem(
    id = id,
    name = name,
    description = description,
    isChecked = isChecked,
)