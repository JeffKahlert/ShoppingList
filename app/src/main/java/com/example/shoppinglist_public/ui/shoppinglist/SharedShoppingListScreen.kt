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
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.einkaufsliste.R
import com.example.shoppinglist_public.ui.data.remote.RemoteItem
import com.example.shoppinglist_public.ui.data.remote.RemoteItemDetails
import com.example.shoppinglist_public.ui.theme.Shapes
import kotlinx.coroutines.launch

@Composable
fun SharedShoppingListScreen(
    //onNavigateToRecipesButton: () -> Unit,
    onNavigateToPrivate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SharedShoppingListViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val sharedShoppingListUiState by viewModel.sharedShoppingItemListUiState.collectAsState()

    Column(
        modifier = modifier
    ) {
        SharedPageNavigationButtons(onNavigateToPrivate = onNavigateToPrivate)
        Box(
            modifier = modifier.padding(top = 2.dp)
        ) {
            SharedShoppingListBody(
                shoppingListItems = sharedShoppingListUiState.items,
                bottomItemUiState = viewModel.sharedListBottomSheetUiState,
                onShowBottomSheet = viewModel::changeSharedBottomSheetUiState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.sendRemoteItem()
                        viewModel.changeSharedBottomSheetUiState(
                            viewModel.sharedListBottomSheetUiState.isBottomSheetVisible
                        )
                    }
                },
                onSheetItemValueChange = viewModel::updateSharedBottomSheetUiState,
                onSwipeToDelete = { item ->
                    coroutineScope.launch {
                        viewModel.removeRemoteItem(item)
                    }
                },
                onSwipeToCheck = { item ->
                    coroutineScope.launch {
                        viewModel.updateRemoteItem(item)
                    }
                },
            )
        }
    }
}

@Composable
fun SharedPageNavigationButtons(
    onNavigateToPrivate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(top = 2.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onNavigateToPrivate,
            modifier = modifier
                .weight(1f)
                .padding(start = 4.dp, end = 2.dp),
            shape = Shapes.medium,
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = stringResource(R.string.private_button),
                color = MaterialTheme.colorScheme.primary
            )
        }
        Button(
            onClick = {},
            modifier = modifier
                .weight(1f)
                .padding(start = 4.dp, end = 2.dp),
            shape = Shapes.medium,
            ) {
            Text(
                text = stringResource(R.string.shared_button),
            )
        }
    }
}

@Composable
fun SharedShoppingListBody(
    shoppingListItems: List<RemoteItem>,
    bottomItemUiState: SharedListBottomSheetUiState,
    onShowBottomSheet: (Boolean) -> Unit,
    onSheetItemValueChange: (RemoteItemDetails) -> Unit,
    onSaveClick: () -> Unit,
    onSwipeToDelete: (RemoteItem) -> Unit,
    onSwipeToCheck: (RemoteItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Box(
            modifier = modifier.weight(0.9f)
        ) {
            LazyColumn {
                items(items = shoppingListItems, key = { it.id }){ item ->
                    SharedSwipeContainer(
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
                    modifier = modifier.weight(0.8f)
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
        SharedScreenBottomModalSheet(
            uiState = bottomItemUiState,
            itemDetails = bottomItemUiState.itemDetails,
            onShowBottomSheet = onShowBottomSheet,
            onSheetItemValueChange = onSheetItemValueChange,
            onSaveClick = onSaveClick
        )
    }
}

@Composable
fun SharedScreenBottomModalSheet(
    uiState: SharedListBottomSheetUiState,
    itemDetails: RemoteItemDetails,
    onShowBottomSheet: (Boolean) -> Unit,
    onSheetItemValueChange: (RemoteItemDetails) -> Unit = {},
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
fun SharedItem(
    item: RemoteItem,
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
            colors = if (item.checked) {
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
                SharedItemInformation(
                    name = item.name,
                    information = item.description,
                    isItemChecked = item.checked,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}


@Composable
fun SharedItemInformation(
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
fun SharedSwipeContainer(
    item: RemoteItem,
    onDelete: (RemoteItem) -> Unit = {},
    onCheck: (RemoteItem) -> Unit = {},
) {
    val context = LocalContext.current
    var hasTriggered by remember { mutableStateOf(false) }

    val confirmValueChange: (SwipeToDismissBoxValue) -> Boolean = remember(item.id) {
        { swipeValue ->
            when (swipeValue) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    if (!hasTriggered) {
                        hasTriggered = true
                        onCheck(item.copy(checked = true))
                    }
                    false
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    if (!hasTriggered) {
                        hasTriggered = true
                        Toast.makeText(context, R.string.deleteArticle, Toast.LENGTH_SHORT).show()
                        onDelete(item)
                    }
                    true
                }
                else -> {
                    hasTriggered = false
                    false
                }
            }
        }
    }



    val swipeToDismissBoxState =
        rememberSwipeToDismissBoxState(
        confirmValueChange = confirmValueChange,
        positionalThreshold = { it * .75f },
        )

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        modifier = Modifier,
        backgroundContent = { SharedSwipeBackground(swipeToDismissBoxState) },
        content = {
            SharedItem(item)
        }
    )
}

@Composable
fun SharedSwipeBackground(
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
fun SharedShoppingAppTopBar(
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