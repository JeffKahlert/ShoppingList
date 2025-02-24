package com.example.einkaufsliste

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.einkaufsliste.ui.screen.AddRecipeScreen
import com.example.einkaufsliste.ui.screen.ShoppingListApp
import com.example.einkaufsliste.ui.theme.EinkaufslisteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EinkaufslisteTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    /*ShoppingListApp(
                        modifier = Modifier.padding(innerPadding)
                    )*/
                    AddRecipeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
