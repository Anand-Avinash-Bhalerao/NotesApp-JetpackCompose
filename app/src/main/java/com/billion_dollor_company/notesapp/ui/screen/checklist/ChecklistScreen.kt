package com.billion_dollor_company.notesapp.ui.screen.checklist

import  android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.billion_dollor_company.notesapp.model.ChecklistInfo
import com.billion_dollor_company.notesapp.ui.screen.components.ConfirmationDialog
import com.billion_dollor_company.notesapp.ui.screen.checklist.components.AddChecklistItemBottomSheet
import com.billion_dollor_company.notesapp.ui.screen.checklist.components.ChecklistItemCard
import com.billion_dollor_company.notesapp.ui.screen.components.CommonScaffold
import com.billion_dollor_company.notesapp.ui.screen.components.ListHolder

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ChecklistScreen(
    viewModel: ChecklistViewModel = hiltViewModel()
) {
    val checklistList = viewModel.checklistList.collectAsState().value
        .sortedWith(compareBy<ChecklistInfo> { it.status })

    var isDeleteDialogOpen by remember {
        mutableStateOf(false)
    }
    var currentSelectedItem by remember {
        mutableStateOf<ChecklistInfo?>(null)
    }

    // to delete a selected note.
    if (isDeleteDialogOpen) {
        ConfirmationDialog(
            onDismissRequest = {
                isDeleteDialogOpen = false
            },
            onConfirmation = {
                isDeleteDialogOpen = false
                if (currentSelectedItem != null) {
                    viewModel.deleteItem(currentSelectedItem!!)
                }
            },
            dialogTitle = "Delete",
            dialogText = "Are you sure you want to delete this item permanently?",
            icon = Icons.Default.Delete
        )
    }

    var isBottomSheetOpen by remember {
        mutableStateOf(false)
    }

    if (isBottomSheetOpen) {
        AddChecklistItemBottomSheet(
            onDismiss = {
                isBottomSheetOpen = false
            },
            onAccept = { checklistInfo ->
                viewModel.addItem(checklistInfo)
                isBottomSheetOpen = false
            }
        )
    }

    CommonScaffold(
        title = "Checklist",
        onFloatingButtonClick = {
            isBottomSheetOpen = true
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
                    items = checklistList,
                    key = {
                        it.uid
                    }
                ) { item ->
                    var isItemCompleted by remember {
                        mutableStateOf(item.status)
                    }
                    ChecklistItemCard(
                        modifier = Modifier
                            .animateItemPlacement(),
                        title = item.name,
                        isChecked = isItemCompleted,
                        shouldStrikeThrough = true,
                        onClicked = {
                            isItemCompleted = !isItemCompleted
                            viewModel.setItemStatus(item)
                        },
                        onLongClicked = {
                            isDeleteDialogOpen = true
                            currentSelectedItem = item
                        }
                    )
                }
            }
        }
    }
}

