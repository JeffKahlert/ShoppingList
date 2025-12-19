@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.shoppinglist_public.ui.shoppinglist


import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.einkaufsliste.R
import com.example.shoppinglist_public.ui.data.local.item.Item
import com.example.shoppinglist_public.ui.data.local.item.ItemDetails
import com.example.shoppinglist_public.ui.theme.EinkaufslisteTheme
import com.example.shoppinglist_public.ui.theme.Shapes
import kotlinx.coroutines.launch

@Composable
fun ShoppingListScreen(
    onNavigateToRecipesButton: () -> Unit,
    onNavigateToShared: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ShoppingListViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val shoppingListUiState by viewModel.shoppingListUiState.collectAsState()

    Column(
        modifier = modifier
    ) {
        PrivatePageNavigationButtons(onNavigateToShared = onNavigateToShared)
        Box(
            modifier = modifier.padding(top = 2.dp)
        ) {
            ShoppingListBody(
                shoppingListItems = shoppingListUiState.items,
                bottomItemUiState = viewModel.bottomSheetUiState,
                onShowBottomSheet = viewModel::changeBottomSheetUiState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.saveItem()
                        viewModel.changeBottomSheetUiState(
                            viewModel.bottomSheetUiState.isBottomSheetVisible
                        )
                    }
                },
                onSheetItemValueChange = viewModel::updateBottomSheetUiState,
                onSwipeToDelete = { item ->
                    coroutineScope.launch {
                        viewModel.removeItem(item)
                    }
                },
                onSwipeToCheck = { item ->
                    coroutineScope.launch {
                        viewModel.updateItem(item)
                    }
                }
            )
        }
    }
}

@Composable
fun PrivatePageNavigationButtons(
    onNavigateToShared: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(top = 2.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { },
            modifier = modifier
                .weight(1f)
                .padding(start = 4.dp, end = 2.dp),
            shape = Shapes.medium,
        ) {
            Text(
                text = stringResource(R.string.private_button),
            )
        }
        Button(
            onClick = onNavigateToShared,
            modifier = modifier
                .weight(1f)
                .padding(start = 4.dp, end = 2.dp),
            shape = Shapes.medium,
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = stringResource(R.string.shared_button),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun ShoppingListBody(
    shoppingListItems: List<Item>,
    bottomItemUiState: BottomSheetUiState,
    onShowBottomSheet: (Boolean) -> Unit,
    onSheetItemValueChange: (ItemDetails) -> Unit,
    onSaveClick: () -> Unit,
    onSwipeToDelete: (Item) -> Unit,
    onSwipeToCheck: (Item) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Box(
            modifier = Modifier.weight(0.9f)
        ) {
            LazyColumn {
                items(items = shoppingListItems, key = { it.id }){ item ->
                    SwipeContainer(
                        item,
                        onDelete = onSwipeToDelete,
                        onCheck = onSwipeToCheck,
                    )
                }
            }
        }
        Box {
            Row {
                Box(
                    modifier = Modifier.weight(0.8f)
                ) {
                    Button(
                        shape = Shapes.medium,
                        onClick = { onShowBottomSheet(bottomItemUiState.isBottomSheetVisible) },
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
        }
    }
    if (bottomItemUiState.isBottomSheetVisible) {
        BottomModalSheet(
            uiState = bottomItemUiState,
            itemDetails = bottomItemUiState.itemDetails,
            onShowBottomSheet = onShowBottomSheet,
            onSheetItemValueChange = onSheetItemValueChange,
            onSaveClick = onSaveClick
        )
    }
}

@Composable
fun SendDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    AlertDialog(
        title = {

        },
        onDismissRequest = {},
        confirmButton = {},
        dismissButton = {}
    )

}

@Composable
fun BottomModalSheet(
    uiState: BottomSheetUiState,
    itemDetails: ItemDetails,
    onShowBottomSheet: (Boolean) -> Unit,
    onSheetItemValueChange: (ItemDetails) -> Unit = {},
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()

    val articleFocusRequester = remember { FocusRequester() }
    val infoFocusRequester = remember { FocusRequester() }

    ModalBottomSheet(
        onDismissRequest = {
            onShowBottomSheet(uiState.isBottomSheetVisible)
        },
        sheetState = sheetState,
    )  {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
                .imePadding()
        ) {
            Text(
                text = stringResource(R.string.addArticle),
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(Modifier.padding(8.dp))
            OutlinedTextField(
                value = itemDetails.name,
                onValueChange = { onSheetItemValueChange(itemDetails.copy(name = it)) },
                label = { Text(text = stringResource(R.string.article)) },
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
                label = { Text(text = stringResource(R.string.info))},
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onSaveClick() }
                ),
                modifier = Modifier.focusRequester(infoFocusRequester)
            )
            Text(text = stringResource(R.string.addInfo))
            Spacer(Modifier.padding(8.dp))

            Button(
                onClick = { onSaveClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = uiState.isEntryValid,
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
                top = dimensionResource(R.dimen.padding_small),
                bottom = dimensionResource(R.dimen.padding_small)
            ),
            colors = if (item.isChecked == 1) {
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            } else {
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            },
            shape = Shapes.medium
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
            ) {
                ItemInformation(
                    name = item.name,
                    information = item.description,
                    isItemChecked = item.isChecked == 1,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}


@Composable
fun ItemInformation(
    name: String,
    information: String?,
    isItemChecked: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        val nameTextStyle = MaterialTheme.typography.displayMedium.copy(
            textDecoration = if (isItemChecked) TextDecoration.LineThrough else TextDecoration.None
        )

        Text(
            text = name,
            style = nameTextStyle
        )

        if (!isItemChecked && information != null) {
            Text(
                text = information,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun SwipeContainer(
    item: Item,
    onDelete: (Item) -> Unit = {},
    onCheck: (Item) -> Unit = {},
) {
    val context = LocalContext.current
    val currentItem by rememberUpdatedState(item)
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onDelete(currentItem)
                Toast.makeText(context, R.string.deleteArticle , Toast.LENGTH_SHORT).show()
                true
            } else if(it == SwipeToDismissBoxValue.StartToEnd) {
                onCheck(currentItem.copy(isChecked = 1))
                false
            } else {
                false
            }
        },
        positionalThreshold = { it * .75f }
    )
    SwipeToDismissBox(
        state = dismissState,
        modifier = Modifier,
        backgroundContent = { SwipeBackground(dismissState) },
        content = {
            Item(item)
        }
    )
}

@Composable
fun SwipeBackground(
    swipeDismissState: SwipeToDismissBoxState
) {
    val color = if(swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        Color.Red
    } else if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd){
        Color.Green
    } else {
        Color.Transparent
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(dimensionResource(R.dimen.padding_medium)),
        contentAlignment = if (color == Color.Red) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Icon(
            imageVector = if (color == Color.Red) Icons.Default.Delete else  Icons.Default.Check,
            contentDescription = "",
            tint = Color.Black
        )
    }
}

@Composable
fun ShoppingAppTopBar(
    onNavigateToRecipesButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.List,
                contentDescription = "",
                modifier = modifier.size(28.dp)
            )
        }

        Text(
            text = stringResource(R.string.list_page_title),
            modifier = Modifier
                .padding(start = 4.dp)
                .align(Alignment.CenterVertically),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(Modifier.weight(1f))

        IconButton(
            onClick = {},
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = ""
            )
        }

        //Spacer(Modifier.weight(1f))
        /*Button(
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
        }*/
    }
}

@Composable
fun SideBar(

) {
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text("Drawer title", modifier = Modifier.padding(16.dp))
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(text = "Drawer Item") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                // ...other drawer items
            }
        }
    ) {
        // Screen content
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
                onNavigateToShared = {},
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
                onNavigateToShared = {},
                Modifier.padding(innerPadding)
            )
        }
    }
}