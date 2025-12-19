package com.example.shoppinglist_public.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shoppinglist_public.ui.recipe.RecipeAddScreen
import com.example.shoppinglist_public.ui.recipe.RecipesScreen
import com.example.shoppinglist_public.ui.shoppinglist.SharedShoppingListScreen
import com.example.shoppinglist_public.ui.shoppinglist.ShoppingListScreen

enum class AppRoutes() {
    ShoppingList,
    Recipe,
    AddRecipe,
    SharedShoppingList
}



@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.SharedShoppingList.name,
        modifier = modifier
    ) {
        composable(route = AppRoutes.ShoppingList.name) {
            ShoppingListScreen(
                onNavigateToRecipesButton = { navController.navigate(AppRoutes.Recipe.name) },
                onNavigateToShared = { navController.navigate(AppRoutes.SharedShoppingList.name) }
            )
        }
        composable(route = AppRoutes.Recipe.name) {
            RecipesScreen(
                onAddRecipeButton = { navController.navigate(AppRoutes.AddRecipe.name) },
                onNavigateToShoppingListButton = { navController.navigate(AppRoutes.ShoppingList.name) }
            )
        }
        composable(route = AppRoutes.AddRecipe.name) {
            RecipeAddScreen(
                onBackButton = { navController.navigate(AppRoutes.Recipe.name) },
                onCancelButton = { navController.navigate(AppRoutes.Recipe.name) }
            )
        }

        composable(route = AppRoutes.SharedShoppingList.name) {
            SharedShoppingListScreen(
                onNavigateToPrivate = { navController.navigate(AppRoutes.ShoppingList.name) }
            )
        }
    }
}