package com.example.einkaufsliste.ui.recipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.einkaufsliste.R
import com.example.einkaufsliste.ui.theme.EinkaufslisteTheme
import com.example.einkaufsliste.ui.theme.Shapes
import kotlinx.coroutines.launch

@Composable
fun RecipeAddScreen(
    onCancelButton: () -> Unit,
    onBackButton: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddRecipeViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
    ) {
        AddRecipeTopBar(
            onBackButton = onBackButton
        )
        HorizontalDivider(thickness = 2.dp)
        RecipeEntryBody(
            recipeAddUiState = viewModel.addRecipeUiState,
            onItemValueChange = viewModel::updateUiSate,
            onCancelButton = onCancelButton,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveItem()
                }
            },
        )

    }

}

@Composable
fun RecipeEntryBody(
    recipeAddUiState: RecipeAddUiState,
    onItemValueChange: (RecipeDetails) -> Unit,
    onCancelButton: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        InputForm(
            recipeDetails = recipeAddUiState.recipeDetails,
            onItemValueChange = onItemValueChange,
        )
        SaveAndCancelButtons(
            onCancelButton = onCancelButton,
            onSaveClick = onSaveClick
        )
    }
}

@Composable
fun InputForm(
    recipeDetails: RecipeDetails,
    onItemValueChange: (RecipeDetails) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_medium) )
    ) {
        Text(
            text = "Name",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        TextField(
            value = recipeDetails.name,
            onValueChange = {
                onItemValueChange(recipeDetails.copy(name = it))
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 4.dp),
            textStyle = TextStyle(
                fontSize = 24.sp
            )
        )

        Text(
            text = "Zutaten",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        TextField(
            value = recipeDetails.ingredients,
            onValueChange = {
                onItemValueChange(recipeDetails.copy(ingredients = it))
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 4.dp),
            textStyle = TextStyle(
                fontSize = 24.sp
            )
        )

        Text(
            text = "Anleitung",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        TextField(
            value = recipeDetails.instruction,
            onValueChange = {
                onItemValueChange(recipeDetails.copy(instruction = it))
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 4.dp),
            textStyle = TextStyle(
                fontSize = 24.sp
            )
        )
    }
}


@Composable
fun AddRecipeTopBar(
    onBackButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackButton
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Spacer(Modifier.weight(1f))
        Text(
            text = stringResource(R.string.recipes),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .padding(16.dp)

        )
    }
}

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeTopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.weight(1f))
                Text(
                    text= "Rezept",
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    )
}*/

@Composable
fun SaveAndCancelButtons(
    onCancelButton: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier.weight(0.5f)
        ) {
            Button(
                onClick = onCancelButton,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small)),
                shape = Shapes.medium
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }

        Box(
            modifier = Modifier.weight(0.5f),
        ) {
            Button(
                onClick = onSaveClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small)),
                shape = Shapes.medium
            ) {
                Text(
                    text = stringResource(R.string.save),
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }
}

@Preview
@Composable
fun AddRecipeScreenDarkTheme() {
    EinkaufslisteTheme(darkTheme = true) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            RecipeAddScreen(
                onCancelButton = {},
                onBackButton = {},
                Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview
@Composable
fun AddRecipeScreenLightTheme() {
    EinkaufslisteTheme(darkTheme = false) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            RecipeAddScreen(
                onCancelButton = {},
                onBackButton = {},
                Modifier.padding(innerPadding)
            )
        }
    }
}
