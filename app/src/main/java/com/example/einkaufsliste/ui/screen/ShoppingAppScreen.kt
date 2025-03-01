package com.example.einkaufsliste.ui.screen

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

enum class AppRoutes() {
    Start,
    Recipe,
    AddRecipe
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShoppingApp(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = AppRoutes.valueOf(
        backStackEntry?.destination?.route ?: AppRoutes.Start.name
    )

    Scaffold(
        modifier = modifier
    ) {
        NavHost(
            navController = navController,
            startDestination = AppRoutes.Start.name,
            modifier = Modifier
        ) {
            composable(route = AppRoutes.Start.name) {
                ShoppingListScreen(
                    onNavigateToRecipesButton = { navController.navigate(AppRoutes.Recipe.name)}
                )
            }

            composable(route = AppRoutes.Recipe.name) {
                RecipesScreen(
                    onAddRecipeButton = { navController.navigate(AppRoutes.AddRecipe.name)},
                    onNavigateToShoppingListButton = { navController.navigate(AppRoutes.Start.name)}
                )
            }

            composable(route = AppRoutes.AddRecipe.name) {
                AddRecipeScreen(
                    onCancelButton = { navController.navigate(AppRoutes.Recipe.name) }
                )
            }
        }
    }
}