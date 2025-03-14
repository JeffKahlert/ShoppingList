@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.einkaufsliste.ui.shoppinglist


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.einkaufsliste.R
import com.example.einkaufsliste.data.model.Item
import com.example.einkaufsliste.ui.theme.EinkaufslisteTheme
import com.example.einkaufsliste.ui.theme.Shapes
import kotlinx.coroutines.launch

/**
 * todo: Refactor
 */

@Composable
fun ShoppingListScreen(
    onNavigateToRecipesButton: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ShoppingListViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(0.1f)
        ) {
            ShoppingAppTopBar(
                onNavigateToRecipesButton = onNavigateToRecipesButton,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Box(
            modifier = Modifier.weight(0.9f)
        ) {
            ShoppingListBody(
                itemUiState = viewModel.shoppingListUiState,
                onShowBottomSheet = viewModel::changeBottomSheetUiState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.saveItem()
                    }
                },
                onSheetItemValueChange = viewModel::updateBottomSheetUiState
            )
        }
    }
}

@Composable
fun ShoppingListBody(
    itemUiState: ShoppingListUiState,
    onShowBottomSheet: (Boolean) -> Unit,
    onSheetItemValueChange: (ItemDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Box(
            modifier = Modifier.weight(0.9f)
        ) {
            LazyColumn(
            ) {
                /*itemsIndexed(
                    items = viewModel.shoppingListUiState.items,
                    key = { _, item -> item.hashCode()}
                ) { _ , currentItem ->
                    SwipeToDeleteContainer(currentItem, onDelete = viewModel::removeItem)
                }*/
            }
        }
        Box {
            Button(
                shape = Shapes.medium,
                onClick = { onShowBottomSheet(itemUiState.isBottomSheetVisible) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                Text(
                    text = stringResource(R.string.add),
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }
    if (itemUiState.isBottomSheetVisible) {
        BottomModalSheet(
            uiState = itemUiState,
            itemDetails = itemUiState.sheetItem,
            onShowBottomSheet = onShowBottomSheet,
            onSheetItemValueChange = onSheetItemValueChange,
            onSaveClick = onSaveClick
        )
    }
    /*uiState.message?.let { message ->
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        viewModel.clearMessage()
    }*/
}

@Composable
fun BottomModalSheet(
    uiState: ShoppingListUiState,
    itemDetails: ItemDetails,
    onShowBottomSheet: (Boolean) -> Unit,
    onSheetItemValueChange: (ItemDetails) -> Unit = {},
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val articleFocusRequester = remember { FocusRequester() }
    val infoFocusRequester = remember { FocusRequester() }

    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet = false
            onShowBottomSheet(uiState.isBottomSheetVisible)
        },
        sheetState = sheetState,
    )  {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
                .imePadding()
        ) {
            Text("Artikel hinzufügen", style = MaterialTheme.typography.displayLarge)
            Spacer(Modifier.padding(8.dp))
            OutlinedTextField(
                value = itemDetails.name,
                onValueChange = {
                    onSheetItemValueChange(itemDetails.copy(name = it))
                    Log.d("EINGABE", itemDetails.name)
                },
                label = { Text("Artikel")},
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction =  ImeAction.Next
                ),
                keyboardActions =  KeyboardActions(
                    onNext = {
                        infoFocusRequester.requestFocus()
                    }
                ),
                modifier = Modifier.focusRequester(articleFocusRequester)
            )
            Spacer(Modifier.padding(8.dp))

            OutlinedTextField(
                value = itemDetails.description,
                onValueChange = { onSheetItemValueChange(itemDetails.copy(description = it))},
                label = { Text("Info -> Optional")},
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        //onSaveClick()
                        //onShowBottomSheet(uiState.isBottomSheetVisible)
                    }
                ),
                modifier = Modifier.focusRequester(infoFocusRequester)
            )
            Spacer(Modifier.padding(8.dp))

            Button(
                onClick = {
                    //onSaveClick()
                    //onShowBottomSheet(uiState.isBottomSheetVisible)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = uiState.isEntryValid
            ) {
                Text(
                    text = stringResource(R.string.add),
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }

}


@Composable
fun Item(
    item: Item,
    modifier: Modifier = Modifier,
) {
    Box {
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
                ItemInformation(
                    item.name,
                    item.description,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                //CheckButton()
            }
        }
    }
}

@Composable
fun ItemInformation(
    name: String,
    information: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.displayMedium,
        )
        if (information != null) {
            Text(
                text = information,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
fun ShoppingAppTopBar(
    onNavigateToRecipesButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.list_page_title),
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(Modifier.weight(1f))
        Button(
            onClick = onNavigateToRecipesButton,
            modifier = Modifier
                .padding(end = 16.dp)
                .align(Alignment.CenterVertically),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
            shape = Shapes.medium
        ) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun CheckButton(
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = {}
    ) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun BottomSheet(bottomSheetVisible: String) {

}

@Composable
fun SwipeToDeleteContainer(
    item: Item,
    onDelete: (Item) -> Unit,
) {
    val context = LocalContext.current
    val currentItem by rememberUpdatedState(item)
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onDelete(currentItem)
                Toast.makeText(context, "Artikel entfernt", Toast.LENGTH_SHORT).show()
                true
            } else {
                false
            }
        },
        positionalThreshold = { it * .75f }
    )
    SwipeToDismissBox(
        state = dismissState,
        modifier = Modifier,
        backgroundContent = { DeleteBackground(dismissState) },
        content = {
            Item(item)
        }
    )
}

@Composable
fun DeleteBackground(
    swipeDismissState: SwipeToDismissBoxState
) {
    val color = if(swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        Color.Transparent
    } else {
        Color.Transparent
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(dimensionResource(R.dimen.padding_medium)),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "",
            tint = Color.Red
        )
    }
}

@Preview
@Composable
fun ShoppingListAppPreviewLight() {
    EinkaufslisteTheme(darkTheme = false) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            ShoppingListScreen(
                onNavigateToRecipesButton = {},
                Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview
@Composable
fun ShoppingListAppPreviewDark() {
    EinkaufslisteTheme(darkTheme = true) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            ShoppingListScreen(
                onNavigateToRecipesButton = {},
                Modifier.padding(innerPadding)
            )
        }
    }
}