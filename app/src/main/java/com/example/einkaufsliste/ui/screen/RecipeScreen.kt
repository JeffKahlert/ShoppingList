package com.example.einkaufsliste.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.einkaufsliste.R
import com.example.einkaufsliste.data.DataSource.dumbRecipes
import com.example.einkaufsliste.model.Recipe
import com.example.einkaufsliste.ui.theme.EinkaufslisteTheme
import com.example.einkaufsliste.ui.theme.Shapes
import com.example.einkaufsliste.ui.viewmodel.RecipeViewModel

/**
 * Boilerplate code from ShoppingListScreen. Need take care of it sometime
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeAppScreen(
    modifier: Modifier = Modifier
) {

    val viewModel: RecipeViewModel = viewModel()

    Column(
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(0.1f)
        ) {
            RecipeAppTopBar(Modifier.fillMaxWidth())
        }
        Box(
            modifier = Modifier.weight(0.9f)
        ) {
            RecipeList()
        }
    }

}

@Composable
fun RecipeList(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.weight(0.9f)
        ) {
            LazyColumn {
                items(dumbRecipes) { recipe ->
                    RecipeItem(recipe)
                }
            }
        }
        Box(
            modifier = Modifier.weight(0.1f)
        ) {
            Button(
                onClick = {},
                shape = Shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                Text(
                    text= stringResource(R.string.add),
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }
}

@Composable
fun RecipeItem(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Box() {
        Card(
            modifier = Modifier.padding(
                start = dimensionResource(R.dimen.padding_medium),
                end = dimensionResource(R.dimen.padding_medium),
                top = dimensionResource(R.dimen.padding_medium)
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            shape = Shapes.medium
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
            ) {
                RecipeInformation(
                    recipe.name,
                    recipe.ingredients,
                    recipe.instruction,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun RecipeInformation(
    name: String,
    ingredients: List<String>,
    instruction: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.displayMedium
        )
        Column {
            Text(text = stringResource(R.string.ingredients),
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                ))
            ingredients.forEach { ingredient ->
                Text(
                    text= ingredient,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
        if (instruction != null) {
            Text(text = stringResource(R.string.instruction),
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                ))
            Text(
                text = instruction,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
fun RecipeAppTopBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Button(
            onClick = {},
            shape = Shapes.medium,
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
        ) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(Modifier.weight(1f))
        Text(
            text = "Rezepte",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}


@Preview
@Composable
fun RecipeListLightTheme() {
    EinkaufslisteTheme(darkTheme = false) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            RecipeAppScreen(Modifier.padding(innerPadding))
        }
    }
}

@Preview
@Composable
fun RecipeListDarkTheme() {
    EinkaufslisteTheme(darkTheme = true) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            RecipeAppScreen(Modifier.padding(innerPadding))
        }
    }
}