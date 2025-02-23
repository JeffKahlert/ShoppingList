package com.example.einkaufsliste.data

import com.example.einkaufsliste.model.DumbItem
import com.example.einkaufsliste.model.Recipe

object DataSource {
    val dumbItems = listOf(
        DumbItem("Apfel", "2 Stück"),
        DumbItem("Bananen", "3 Stück"),
        DumbItem("Schwarzer Tee", ""),
        DumbItem("Frühstück", "Aufschnitt"),
        DumbItem("Waschmittel", "Ariel")
    )

    val dumbRecipes = listOf(
        Recipe("One", listOf("One", "One"), "one"),
        Recipe("two", listOf("two", "two"), "two"),
        Recipe("three", listOf("three", "three"), "three"),
        Recipe("four", listOf("four", "four"), "four"),
        Recipe("five", listOf("five", "five"), "five")
    )
}