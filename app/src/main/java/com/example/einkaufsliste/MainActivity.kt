package com.example.einkaufsliste

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.einkaufsliste.ui.navigation.AppNavHost
import com.example.einkaufsliste.ui.screen.ShoppingApp
import com.example.einkaufsliste.ui.screen.ShoppingListScreen
import com.example.einkaufsliste.ui.theme.EinkaufslisteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EinkaufslisteTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    /*ShoppingListScreen(
                        onNavigateToRecipesButton = {  },
                        modifier = Modifier.padding(innerPadding)
                    )*/
                   ShoppingApp(Modifier.padding(innerPadding))
                }
            }
        }
    }
}
