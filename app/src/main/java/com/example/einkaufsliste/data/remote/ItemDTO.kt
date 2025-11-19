package com.example.einkaufsliste.data.remote

import com.google.gson.annotations.SerializedName

data class ItemDTO(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("isChecked")
    val isChecked: Int = 0
)