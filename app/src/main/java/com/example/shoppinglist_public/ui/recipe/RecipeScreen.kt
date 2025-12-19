package com.example.shoppinglist_public.ui.recipe

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.einkaufsliste.R
import com.example.shoppinglist_public.ui.data.local.recipe.RecipeWithIngredients
import com.example.shoppinglist_public.ui.theme.EinkaufslisteTheme
import com.example.shoppinglist_public.ui.theme.Shapes

/**
 * Boilerplate code from ShoppingListScreen. Need take care of it sometime
 */

@Composable
fun RecipesScreen(
    onNavigateToShoppingListButton: () -> Unit,
    onAddRecipeButton: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val recipeUiState by viewModel.recipeListUiState.collectAsState()

    Column(
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(0.1f)
        ) {
            RecipeAppTopBar(
                onNavigateToShoppingListButton = onNavigateToShoppingListButton,
                Modifier.fillMaxWidth()
            )
        }
        Box(
            modifier = Modifier.weight(0.9f)
        ) {
            RecipeScreenBody(
                recipeList = recipeUiState.recipeList
            )
        }
        Box{
            Button(
                onClick = onAddRecipeButton,
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
fun RecipeScreenBody(
    recipeList: List<RecipeWithIngredients>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Box(
        ) {
            LazyColumn {
                items(recipeList) { recipe ->
                    RecipeItem(recipe)
                }
            }
        }

    }
}

@Composable
fun RecipeItem(
    recipeWithIngredients: RecipeWithIngredients,
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
                    name = recipeWithIngredients.recipe.name,
                    ingredients = "recipe.ingredients",
                    instruction = recipeWithIngredients.recipe.instruction,
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
    ingredients: String,
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
            //ingredients.forEach { ingredient ->
                Text(
                    text= ingredients,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge,
                )
            //}
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
    onNavigateToShoppingListButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Button(
            onClick = onNavigateToShoppingListButton,
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
            text = stringResource(R.string.recipes),
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
            RecipesScreen(
                onNavigateToShoppingListButton = {},
                onAddRecipeButton = {},
                Modifier.padding(innerPadding)
            )
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
            RecipesScreen(
                onNavigateToShoppingListButton = {},
                onAddRecipeButton = {},
                Modifier.padding(innerPadding)
            )
        }
    }
}