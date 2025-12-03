package com.example.einkaufsliste

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.einkaufsliste.ui.navigation.AppNavHost

@Composable
fun ShoppingListApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    AppNavHost(modifier = modifier, navController = navController)
}