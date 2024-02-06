package com.billion_dollor_company.notesapp.ui.screen.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
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
import com.billion_dollor_company.notesapp.model.TasksInfo
import com.billion_dollor_company.notesapp.ui.screen.components.ConfirmationDialog
import com.billion_dollor_company.notesapp.ui.screen.tasks.components.TaskCard
import com.billion_dollor_company.notesapp.ui.screen.components.CommonScaffold
import com.billion_dollor_company.notesapp.ui.screen.components.EmptyLogo
import com.billion_dollor_company.notesapp.ui.screen.components.ListHolder
import com.billion_dollor_company.notesapp.ui.screen.tasks.components.AddTaskBottomSheet
import com.billion_dollor_company.notesapp.util.Constants

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskScreen(
    viewModel: TasksViewModel = hiltViewModel()
) {
    // this contains the entire list of tasks.
    val tasksList = viewModel.taskInfoList.collectAsState().value
        .sortedWith(compareBy<TasksInfo> { it.status }.thenBy { it.priority }.thenBy { it.title })

    // used for opening the delete alert dialog. if set to true then dialog will open.
    var isDeleteDialogOpen by remember {
        mutableStateOf(false)
    }

    var currentSelectedTask by remember {
        mutableStateOf<TasksInfo?>(null)
    }

    // to delete a selected note.
    if (isDeleteDialogOpen) {
        ConfirmationDialog(
            onDismissRequest = {
                isDeleteDialogOpen = false
            },
            onConfirmation = {
                isDeleteDialogOpen = false
                if (currentSelectedTask != null) {
                    viewModel.deleteTask(currentSelectedTask!!)
                }
            },
            dialogTitle = "Delete",
            dialogText = "Are you sure you want to delete this task permanently?",
            icon = Icons.Default.Delete
        )
    }

    // variables for opening add tasks bottom sheet.
    // if set to true bottom sheet opens
    var isBottomSheetOpen by remember {
        mutableStateOf(false)
    }
    if (isBottomSheetOpen) {
        AddTaskBottomSheet(
            onDismiss = {
                isBottomSheetOpen = false
            },
            onAccept = { task ->
                viewModel.addTask(task)
                isBottomSheetOpen = false
            }
        )
    }

    CommonScaffold(
        title = Constants.Tasks.Labels.PAGE_LABEL,
        onFloatingButtonClick = {
            isBottomSheetOpen = true
        },
        floatingButtonIcon = Icons.Default.AddTask
    ) { paddingValues ->
        ListHolder(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp)
        ) {
            if (tasksList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    items(
                        items = tasksList,
                        key = {
                            it.uid
                        }
                    ) { task ->
                        var isChecked by remember {
                            mutableStateOf(task.status)
                        }
                        TaskCard(
                            modifier = Modifier
                                .animateItemPlacement(),
                            title = task.title,
                            isChecked = isChecked,
                            shouldStrikeThrough = true,
                            onClicked = {
                                isChecked = !isChecked
                                viewModel.setTaskStatus(task)
                            },
                            taskPriority = task.priority,
                            onLongClicked = {
                                isDeleteDialogOpen = true
                                currentSelectedTask = task
                            }
                        )
                    }
                }
            }else{
                EmptyLogo()
            }
        }
    }
}
