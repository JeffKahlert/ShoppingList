package com.example.einkaufsliste.model

data class Recipe(
    val name: String = "",
    val ingredients: List<String> = emptyList(),
    val instruction: String = ""
)
