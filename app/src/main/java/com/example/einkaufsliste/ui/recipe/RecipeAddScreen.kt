package com.example.einkaufsliste.ui.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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
    modifier: Modifier = Modifier,
    recipeAddUiState: RecipeAddUiState,
    onItemValueChange: (RecipeDetails) -> Unit,
    onSaveClick: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.weight(0.9f)
        ) {
            InputForm(
                recipeDetails = recipeAddUiState.recipeDetails,
                onItemValueChange = onItemValueChange,
            )
        }
        Box {
            SaveButtons(
                onSaveClick = onSaveClick
            )
        }
    }
}

@Composable
fun InputForm(
    modifier: Modifier = Modifier,
    recipeDetails: RecipeDetails,
    onItemValueChange: (RecipeDetails) -> Unit = {},
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            label = { Text(stringResource(R.string.name))},
            value = recipeDetails.name,
            onValueChange = {
                onItemValueChange(recipeDetails.copy(name = it))
            },
            modifier = Modifier
                .fillMaxSize()
            ,
            shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium)),
        )
        Spacer(Modifier.padding(top = 8.dp))
        Row(
        ) {
            OutlinedTextField(
                label = { Text("minuten") },
                value = recipeDetails.name,
                onValueChange = {},
                shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium)),
                modifier = Modifier.weight(0.25f)
            )
            Spacer(Modifier.padding(end = 8.dp))
            OutlinedTextField(
                label = { Text("1") },
                value = recipeDetails.name,
                onValueChange = {},
                shape = RoundedCornerShape(
                    topStart = dimensionResource(R.dimen.padding_medium),
                    bottomStart = dimensionResource(R.dimen.padding_medium)

                ),
                modifier = Modifier.weight(0.2f)
            )
            OutlinedTextField(
                label = { Text("Portionen") },
                value = recipeDetails.name,
                onValueChange = {},
                shape = RoundedCornerShape(
                    topEnd = dimensionResource(R.dimen.padding_medium),
                    bottomEnd = dimensionResource(R.dimen.padding_medium)

                ),
                modifier = Modifier.weight(0.35f)
            )
        }


        Spacer(Modifier.padding(top = 16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium))
                )
                .clickable {
                },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Zutaten",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(20.dp)
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }

        }
        Spacer(Modifier.padding(top = 16.dp))
        Text(
            text = "Anleitung",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Spacer(Modifier.padding(top = 16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {},
                shape = Shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                ),
                modifier = Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    shape = Shapes.medium
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(Modifier.padding(4.dp))
                Text(
                    text = stringResource(R.string.add),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        /*OutlinedTextField(
            value = recipeDetails.instruction,
            onValueChange = {
                onItemValueChange(recipeDetails.copy(instruction = it))
            },
            modifier = Modifier
                .fillMaxSize()
        )*/
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
            text = stringResource(R.string.recipe),
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
fun SaveButtons(
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onSaveClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        shape = Shapes.medium
    ) {
        Text(
            text = stringResource(R.string.save),
            style = MaterialTheme.typography.displayMedium
        )
    }

}


@Preview
@Composable
fun InputFormPreview() {
    EinkaufslisteTheme(darkTheme = true) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            InputForm(
                modifier = Modifier.padding(innerPadding),
                recipeDetails = RecipeDetails(),
                onItemValueChange = {}
            )
        }
    }
}
