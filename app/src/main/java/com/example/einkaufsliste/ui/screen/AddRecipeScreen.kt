package com.example.einkaufsliste.ui.screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.einkaufsliste.R
import com.example.einkaufsliste.ui.theme.EinkaufslisteTheme
import com.example.einkaufsliste.ui.theme.Shapes
import com.example.einkaufsliste.ui.viewmodel.AddRecipeViewModel
import kotlinx.coroutines.flow.map

@Composable
fun AddRecipeScreen(
    onCancelButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: AddRecipeViewModel = viewModel()

    Column(
        modifier = modifier
    ) {
        AddRecipeTopBar()
        HorizontalDivider(thickness = 2.dp)
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            EnterTextComposable(viewModel, stringResource(R.string.name))
            EnterTextComposable(viewModel, stringResource(R.string.ingredients))
            EnterTextComposable(viewModel, stringResource(R.string.instruction))

        }
        Spacer(Modifier.weight(1f))
        SaveAndCancelButtons(
            onCancelButton = onCancelButton,
        )
    }

}

@Composable
fun EnterTextComposable(
    viewModel: AddRecipeViewModel,
    textFieldName: String,
    modifier: Modifier = Modifier
) {

    // Damit ViewModel und UIState auf dem gleichen Stand sind
    val content by viewModel.addRecipeUiState
        .map { state ->
            when (textFieldName) {
                "Name" -> state.name
                "Zutaten" -> state.ingredient
                "Anweisung" -> state.instruction
                else -> ""
            }
        }
        .collectAsState(initial = "")

    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        Text(
            text = textFieldName,
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        TextField(
            value = content,
            onValueChange = {
                checkInput(textFieldName, it, viewModel)
            },
            label = { Text("")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
    }
}

fun checkInput(field: String, value: String, viewModel: AddRecipeViewModel) {
    when (field) {
        "Name" -> viewModel.updateNameTextFieldValue(value)
        "Zutaten" -> viewModel.updateIngredientTextFieldValue(value)
        "Anweisung" -> viewModel.updateInstructionTextFieldValue(value)
    }
}

@Composable
fun AddRecipeTopBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {}) {
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
                onClick = {},
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
            AddRecipeScreen(
                onCancelButton = {},
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
            AddRecipeScreen(
                onCancelButton = {},
                Modifier.padding(innerPadding)
            )
        }
    }
}
