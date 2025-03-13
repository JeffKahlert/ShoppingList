package com.example.einkaufsliste.data

import com.example.einkaufsliste.data.model.Item
import com.example.einkaufsliste.data.model.Recipe

object DataSource {
    val dumbItems = listOf(
        Item("Apfel", "2 Stück"),
        Item("Bananen", "3 Stück"),
        Item("Schwarzer Tee", ""),
        Item("Frühstück", "Aufschnitt"),
        Item("Waschmittel", "Ariel")
    )

    val dumbRecipes = listOf(
        Recipe("One", listOf("One", "One"), "one"),
        Recipe("two", listOf("two", "two"), "two"),
        Recipe("three", listOf("three", "three"), "three"),
        Recipe("four", listOf("four", "four"), "four"),
        Recipe("five", listOf("five", "five"), "five")
    )
}