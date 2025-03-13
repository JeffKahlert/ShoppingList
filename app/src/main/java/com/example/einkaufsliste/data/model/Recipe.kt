package com.example.einkaufsliste.data.model

data class Recipe(
    val name: String = "",
    val ingredients: List<String> = emptyList(),
    val instruction: String = ""
)
