package com.example.shoppinglist_public.ui.recipe

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.einkaufsliste.R
import com.example.shoppinglist_public.ui.theme.EinkaufslisteTheme
import com.example.shoppinglist_public.ui.theme.Shapes
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
            onShowInstructionSheet = viewModel::changeInstructionBottomModalState,
            onShowEditSheet = viewModel::changeEditInstructionBottomModalState,
            instructionBottomSheetUiState = viewModel.instructionBottomModalUiState,
            editUiState = viewModel.editInstructionBottomModalUiState,
            onItemValueChange = viewModel::updateUiState,
            onInstructionValueChange = viewModel::updateInstructionUiState,
            onEditInstructionValueChange = viewModel::updateEditInstructionUiState,
            onUpdateEditInstruction = viewModel::updateUiState,
            onTransferUiStates = viewModel::transferRecipeUiStateToEditUiState,
            ingredientUiState = viewModel.ingredientBottomModalUiState,
            onShowIngredientSheet = viewModel::changeIngredientsBottomModalState,
            onAddIngredient = viewModel::addIngredient,
            onTypeIngredientDetails = viewModel::updateIngredientUiState,
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
    onShowInstructionSheet: (Boolean) -> Unit,
    onShowEditSheet: (Boolean) -> Unit,
    onInstructionValueChange: (InstructionDetails) -> Unit,
    onEditInstructionValueChange: (RecipeDetails) -> Unit,
    editUiState: EditInstructionBottomModalUiState,
    instructionBottomSheetUiState: InstructionBottomModalUiState,
    onItemValueChange: (RecipeDetails) -> Unit,
    onSaveClick: () -> Unit,
    onTransferUiStates: () -> Unit,
    onUpdateEditInstruction: (RecipeDetails) -> Unit,
    ingredientUiState: IngredientBottomModalUiState,
    onShowIngredientSheet: (Boolean) -> Unit,
    onAddIngredient: (IngredientDetails, Set<Map<String, String>>) -> Unit,
    onTypeIngredientDetails: (IngredientDetails, Set<Map<String, String>>) -> Unit,
    ) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.weight(0.9f)
        ) {
            InputForm(
                recipeDetails = recipeAddUiState.recipeDetails,
                instructionBottomSheetUiState = instructionBottomSheetUiState,
                editBottomSheetUiState = editUiState,
                ingredientBottomModalUiState = ingredientUiState,
                onShowEditSheet = onShowEditSheet,
                onShowInstructionBottomSheet = onShowInstructionSheet,
                onShowIngredientSheet = onShowIngredientSheet,
                onItemValueChange = onItemValueChange,
                onTransferUiStates = onTransferUiStates
            )
        }
        Box {
            SaveButtons(
                onSaveClick = onSaveClick
            )
        }

        if (instructionBottomSheetUiState.isVisible) {
            InstructionBottomModalSheet(
                instructionDetails = instructionBottomSheetUiState.instruction,
                onShowInstructionSheet = onShowInstructionSheet,
                instructionBottomSheetUiState = instructionBottomSheetUiState,
                onInstructionValueChange = onInstructionValueChange,
                onRecipeItemValueChange = onItemValueChange,
                recipeDetails = recipeAddUiState.recipeDetails,
            )
        }

        if (editUiState.isVisible) {
            Log.d("UI", "editUiState.recipeDetails.instruction = ${editUiState.recipeDetails.instruction}")
            EditInstructionBottomModalSheet(
                recipeDetails = editUiState.recipeDetails,
                editUiState = editUiState,
                onShowBottomSheet = onShowEditSheet,
                onEditItemValueChange = onEditInstructionValueChange,
                onUpdateUiState = onUpdateEditInstruction
            )
        }

        if (ingredientUiState.isVisible) {
            IngredientsBottomModalSheet(
                onShowBottomSheet = onShowIngredientSheet,
                ingredientUiState = ingredientUiState,
                onAddIngredient = onAddIngredient,
                onTypeIngredientDetails = onTypeIngredientDetails,
                ingredientDetails = ingredientUiState.ingredientDetails,
            )
        }
    }
}

@Composable
fun InputForm(
    modifier: Modifier = Modifier,
    instructionBottomSheetUiState: InstructionBottomModalUiState,
    editBottomSheetUiState: EditInstructionBottomModalUiState,
    ingredientBottomModalUiState: IngredientBottomModalUiState,
    recipeDetails: RecipeDetails,
    onShowInstructionBottomSheet: (Boolean) -> Unit = {},
    onShowEditSheet: (Boolean) -> Unit,
    onShowIngredientSheet: (Boolean) -> Unit,
    onTransferUiStates: () -> Unit,
    onItemValueChange: (RecipeDetails) -> Unit = {},
) {
    var isAmountTextFieldFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            label = { Text(stringResource(R.string.name))},
            value = recipeDetails.name,
            onValueChange = { onItemValueChange(recipeDetails.copy(name = it)) },
            modifier = Modifier
                .fillMaxSize()
            ,
            shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium)),
        )
        Spacer(Modifier.padding(top = 8.dp))
        Row(
        ) {
            OutlinedTextField(
                label = { Text("Minuten") },
                value = recipeDetails.duration,
                onValueChange = { onItemValueChange(recipeDetails.copy(duration = it)) },
                shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium)),
                modifier = Modifier.weight(0.25f)
            )
            Spacer(Modifier.padding(end = 8.dp))
            OutlinedTextField(
                label = { Text(if (isAmountTextFieldFocused) "Anzahl" else "1") },
                value = recipeDetails.amount,
                onValueChange = { onItemValueChange(recipeDetails.copy(amount = it)) },
                shape = RoundedCornerShape(
                    topStart = dimensionResource(R.dimen.padding_medium),
                    bottomStart = dimensionResource(R.dimen.padding_medium)
                ),
                modifier = Modifier
                    .weight(0.2f)
                    .onFocusChanged { isAmountTextFieldFocused = it.isFocused }
            )
            OutlinedTextField(
                label = { Text("Portionen") },
                value = recipeDetails.portions,
                onValueChange = { onItemValueChange(recipeDetails.copy(portions = it)) },
                shape = RoundedCornerShape(
                    topEnd = dimensionResource(R.dimen.padding_medium),
                    bottomEnd = dimensionResource(R.dimen.padding_medium)

                ),
                modifier = Modifier.weight(0.35f)
            )
        }
        Spacer(Modifier.padding(top = 16.dp))
        IngredientCard(
            isBottomSheetVisible = onShowIngredientSheet,
            ingredientUiState = ingredientBottomModalUiState
        )
        Spacer(Modifier.padding(top = 16.dp))
        Text(
            text = stringResource(R.string.instruction),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Spacer(Modifier.padding(top = 16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (recipeDetails.instruction.isEmpty()) {
                Button(
                    onClick = { onShowInstructionBottomSheet(instructionBottomSheetUiState.isVisible) },
                    shape = Shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                    ),
                    modifier = Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        shape = Shapes.medium
                    ),
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
        }
        Spacer(Modifier.padding(top = 16.dp))
        if (recipeDetails.instruction.isNotEmpty()) {
            InstructionCard(
                recipeDetails = recipeDetails,
                onShowBottomSheet = onShowEditSheet,
                editBottomSheetUiState = editBottomSheetUiState,
                onTransferUiStates = onTransferUiStates
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionBottomModalSheet(
    instructionDetails: InstructionDetails,
    recipeDetails: RecipeDetails,
    onShowInstructionSheet: (Boolean) -> Unit,
    onInstructionValueChange: (InstructionDetails) -> Unit,
    onRecipeItemValueChange: (RecipeDetails) -> Unit,
    instructionBottomSheetUiState: InstructionBottomModalUiState,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomModal by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = {
            showBottomModal = false
            onShowInstructionSheet(instructionBottomSheetUiState.isVisible)
        },
        sheetState = sheetState,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Anweisung",
                    style = MaterialTheme.typography.displayMedium
                )
                Spacer(Modifier.weight(1f))
                IconButton(
                    onClick = {
                        onRecipeItemValueChange(recipeDetails.copy(
                            instruction = instructionDetails.instruction
                        ))
                        showBottomModal = false
                        onShowInstructionSheet(instructionBottomSheetUiState.isVisible)
                    },
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        shape = Shapes.medium
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
            }
            Spacer(Modifier.padding(16.dp))
            OutlinedTextField(
                value = instructionDetails.instruction,
                onValueChange = {
                    onInstructionValueChange(instructionDetails.copy(instruction = it))
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditInstructionBottomModalSheet(
    recipeDetails: RecipeDetails,
    editUiState: EditInstructionBottomModalUiState,
    onEditItemValueChange: (RecipeDetails) -> Unit,
    onShowBottomSheet: (Boolean) -> Unit,
    onUpdateUiState: (RecipeDetails) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomModal by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = {
            showBottomModal = false
            onShowBottomSheet(editUiState.isVisible)
        },
        sheetState = sheetState,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Anweisung",
                    style = MaterialTheme.typography.displayMedium
                )
                Spacer(Modifier.weight(1f))
                IconButton(
                    onClick = {
                        onUpdateUiState(recipeDetails.copy(
                            instruction = recipeDetails.instruction
                        ))
                        showBottomModal = false
                        onShowBottomSheet(editUiState.isVisible)
                    },
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        shape = Shapes.medium
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
            }
            Spacer(Modifier.padding(16.dp))
            OutlinedTextField(
                value = recipeDetails.instruction,
                onValueChange = {
                    onEditItemValueChange(recipeDetails.copy(instruction = it))
                },
                modifier = Modifier.fillMaxSize()
            )
            LaunchedEffect(recipeDetails) {
                Log.d("UI", "EditInstructionBottomModalSheet: instruction = ${recipeDetails.instruction}")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientsBottomModalSheet(
    modifier: Modifier = Modifier,
    ingredientDetails: IngredientDetails,
    onShowBottomSheet: (Boolean) -> Unit,
    onAddIngredient: (IngredientDetails, Set<Map<String, String>>) -> Unit,
    onTypeIngredientDetails: (IngredientDetails, Set<Map<String, String>>) -> Unit,
    ingredientUiState: IngredientBottomModalUiState,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }

    var isAmountTextFieldFocused by remember { mutableStateOf(false) }
    var isIngredientTextFieldFocused by remember { mutableStateOf(false) }

    var amount by remember { mutableStateOf("") }
    var ingredient by remember { mutableStateOf("") }

    ModalBottomSheet(
        modifier = Modifier.fillMaxSize(),
        onDismissRequest = {
            showBottomSheet = false
            onShowBottomSheet(ingredientUiState.isVisible)
        },
        sheetState = sheetState
    ) {
        Column (
            modifier = Modifier.padding(8.dp)
        ){
            Text(
                text = "Zutaten",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(Modifier.padding(8.dp))
            HorizontalDivider(thickness = 1.dp)
            Spacer(Modifier.padding(8.dp))
            Box(
                modifier = Modifier.weight(0.7f)
            ) {
                LazyColumn {
                    items(ingredientUiState.ingredients.toList()) { ingredientMap ->
                        Log.d("Column", ingredientMap.toString())
                        ingredientMap.forEach { (ingredient, amount) ->
                            IngredientCardForBottomSheet(
                                amount = amount,
                                ingredient = ingredient
                            )
                        }
                    }
                }
            }
            HorizontalDivider(thickness = 1.dp)
            Spacer(Modifier.padding(4.dp))
            Box {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(1.5f)
                    ) {
                        OutlinedTextField(
                            value = ingredientDetails.amount,
                            onValueChange = {
                                onTypeIngredientDetails(
                                    ingredientDetails.copy(amount = it),
                                    ingredientUiState.ingredients,
                                )
                                amount = it
                            },
                            placeholder = { Text("2 EL") },
                            label = { Text( if(isAmountTextFieldFocused)"Menge" else "Menge")},
                            shape = RoundedCornerShape(
                                topStart = dimensionResource(R.dimen.padding_medium),
                                bottomStart = dimensionResource(R.dimen.padding_medium)
                            ),
                            modifier = Modifier.onFocusChanged {
                                isAmountTextFieldFocused = it.isFocused
                            }
                        )
                    }
                    Box(
                        modifier = Modifier.weight(3f)
                    ){
                        OutlinedTextField(
                            value = ingredientDetails.ingredient,
                            onValueChange = {
                                onTypeIngredientDetails(
                                    ingredientDetails.copy(ingredient = it ),
                                    ingredientUiState.ingredients
                                )
                                ingredient = it
                            },
                            placeholder = { Text("Zucker")},
                            label = { Text( if(isIngredientTextFieldFocused)"Zutat" else "Zutat")},
                            shape = RoundedCornerShape(
                                topEnd = dimensionResource(R.dimen.padding_medium),
                                bottomEnd = dimensionResource(R.dimen.padding_medium),
                            ),
                            modifier = Modifier.onFocusChanged {
                                isIngredientTextFieldFocused = it.isFocused
                            }
                        )
                    }
                    Box(
                        //modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center,
                    ){
                        IconButton(
                            onClick = {
                                onAddIngredient(
                                    IngredientDetails(amount = amount, ingredient = ingredient),
                                    ingredientUiState.ingredients
                                )
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun InstructionCard(
    recipeDetails: RecipeDetails,
    onShowBottomSheet: (Boolean) -> Unit,
    editBottomSheetUiState: EditInstructionBottomModalUiState,
    onTransferUiStates: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(
            //containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        )  {
            Box(
                modifier = Modifier.weight(0.8f)
            ) {
                Column(
                    modifier = Modifier
                        .height(300.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = recipeDetails.instruction
                    )
                }
            }
            Box {
                IconButton(
                    onClick = {
                        onShowBottomSheet(editBottomSheetUiState.isVisible)
                        onTransferUiStates()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = ""
                    )
                }
            }
        }
    }

}

@Composable
fun IngredientCard(
    isBottomSheetVisible: (Boolean) -> Unit,
    ingredientUiState: IngredientBottomModalUiState,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium))
            )
            .clickable {
                isBottomSheetVisible(ingredientUiState.isVisible)
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            )
        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.ingredients),
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
}

@Composable
fun IngredientCardForBottomSheet(
    modifier: Modifier = Modifier,
    amount: String,
    ingredient: String
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = amount
            )
            Text(
                text = ingredient
            )
            Spacer(Modifier.weight(1f))
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = ""
                )
            }
        }
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
                instructionBottomSheetUiState = InstructionBottomModalUiState(),
                onShowEditSheet = {},
                editBottomSheetUiState = EditInstructionBottomModalUiState(),
                onTransferUiStates = {},
                ingredientBottomModalUiState = IngredientBottomModalUiState(),
                onItemValueChange = {},
                onShowIngredientSheet = {}
            )
        }
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

