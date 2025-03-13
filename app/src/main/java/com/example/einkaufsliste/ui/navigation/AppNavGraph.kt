package com.example.einkaufsliste.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.einkaufsliste.ui.recipe.RecipeAddScreen
import com.example.einkaufsliste.ui.recipe.RecipesScreen
import com.example.einkaufsliste.ui.shoppinglist.ShoppingListScreen

enum class AppRoutes() {
    ShoppingList,
    Recipe,
    AddRecipe
}



@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.ShoppingList.name,
        modifier = modifier
    ) {
        composable(route = AppRoutes.ShoppingList.name) {
            ShoppingListScreen(
                onNavigateToRecipesButton = { navController.navigate(AppRoutes.Recipe.name) }
            )
        }
        composable(route = AppRoutes.Recipe.name) {
            RecipesScreen(
                onAddRecipeButton = { navController.navigate(AppRoutes.AddRecipe.name) },
                onNavigateToShoppingListButton = { navController.navigate(AppRoutes.ShoppingList.name)}
            )
        }
        composable(route = AppRoutes.AddRecipe.name) {
            RecipeAddScreen(
                onBackButton = { navController.navigate(AppRoutes.Recipe.name) },
                onCancelButton = { navController.navigate(AppRoutes.Recipe.name)}
            )
        }
    }
}