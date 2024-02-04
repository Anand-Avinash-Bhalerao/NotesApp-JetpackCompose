package com.billion_dollor_company.notesapp.ui.screen.toBuy

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Balance
import androidx.compose.material.icons.outlined.EmojiFoodBeverage
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.billion_dollor_company.notesapp.model.ToBuyInfo
import com.billion_dollor_company.notesapp.ui.components.OpenDialog
import com.billion_dollor_company.notesapp.ui.components.OutlinedInputText
import com.billion_dollor_company.notesapp.ui.screen.toBuy.components.ToBuyItemCard
import com.billion_dollor_company.notesapp.ui.screen.components.CommonScaffold
import com.billion_dollor_company.notesapp.ui.screen.components.ListHolder

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun GroceryScreen(
    viewModel: ToBuyViewModel = hiltViewModel()
) {
    val toBuyList = viewModel.toBuyInfoList.collectAsState().value
        .sortedWith(compareBy<ToBuyInfo> { it.status })

    var openAlertDialog by remember {
        mutableStateOf(false)
    }
    var currentSelectedToBuyItem by remember {
        mutableStateOf<ToBuyInfo?>(null)
    }

    // to delete a selected note.
    if (openAlertDialog) {
        OpenDialog(
            onDismissRequest = {
                openAlertDialog = false
            },
            onConfirmation = {
                openAlertDialog = false
                if (currentSelectedToBuyItem != null) {
                    viewModel.deleteToBuyItem(currentSelectedToBuyItem!!)
                }
            },
            dialogTitle = "Delete",
            dialogText = "Are you sure you want to delete this item permanently?",
            icon = Icons.Default.Delete
        )
    }
    var openToBuyBottomSheet by remember {
        mutableStateOf(false)
    }
    if (openToBuyBottomSheet) {
        AddItem(
            onDismiss = {
                openToBuyBottomSheet = false
            },
            onAccept = { grocery ->
                viewModel.addToBuyItem(grocery)
                openToBuyBottomSheet = false
            }
        )
    }

    CommonScaffold(
        title = "Checklist",
        onFABClick = {
            openToBuyBottomSheet = true
        }
    )
    { paddingValues ->
        ListHolder(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                items(
                    items = toBuyList,
                    key = {
                        it.uid
                    }
                ) { grocery ->
                    var isChecked by remember {
                        mutableStateOf(grocery.status)
                    }
                    ToBuyItemCard(
                        modifier = Modifier
//                            .fillMaxWidth()
                            .animateItemPlacement(),
                        title = grocery.name,
                        quantity = grocery.quantity,
                        isChecked = isChecked,
                        shouldStrikeThrough = true,
                        onClicked = {
                            isChecked = !isChecked
                            viewModel.setToBuyStatus(grocery)
                        },
                        onLongClicked = {
                            openAlertDialog = true
                            currentSelectedToBuyItem = grocery
                        }
                    )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItem(
    onDismiss: () -> Unit,
    onAccept: (ToBuyInfo) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    Column {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = bottomSheetState,
            dragHandle = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                    Text(
                        text = "Add new Item",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        ) {
            var currentName by remember {
                mutableStateOf("")
            }

            var currentQuantity by remember {
                mutableStateOf("")
            }

            Column {
                val focusRequester = remember {
                    FocusRequester()
                }
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
                OutlinedInputText(
                    text = currentName,
                    imageVector = Icons.Outlined.EmojiFoodBeverage,
                    placeHolder = "Enter item name here",
                    imeAction = ImeAction.Next,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .padding(top = 16.dp)
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),

                    ) { newText ->
                    currentName = newText
                }

                OutlinedInputText(
                    text = currentQuantity,
                    imageVector = Icons.Outlined.Balance,
                    placeHolder = "Enter quantity here",
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 4.dp)
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) { newText ->
                    currentQuantity = newText
                }



                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FilledTonalButton(
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete item button icon"
                        )
                        Spacer(
                            modifier = Modifier.size(ButtonDefaults.IconSpacing)
                        )
                        Text(text = "Discard")
                    }
                    Button(
                        onClick = {
                            onAccept(
                                ToBuyInfo(
                                    name = currentName,
                                    quantity = currentQuantity
                                )
                            )
                        },
                        contentPadding = ButtonDefaults.ButtonWithIconContentPadding
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "Add task button icon"
                        )
                        Spacer(
                            modifier = Modifier.size(ButtonDefaults.IconSpacing)
                        )
                        Text(text = "Add")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
