package com.example.shoppinglist_public.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist_public.ui.navigation.AppNavHost

@Composable
fun ShoppingListApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    AppNavHost(modifier = modifier, navController = navController)
}