package com.billion_dollor_company.notesapp.ui.screen.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.billion_dollor_company.notesapp.model.NoteInfo
import com.billion_dollor_company.notesapp.model.TasksInfo
import com.billion_dollor_company.notesapp.ui.components.ListHolder
import com.billion_dollor_company.notesapp.ui.components.OpenDialog
import com.billion_dollor_company.notesapp.ui.components.TextCheckBox


@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskSection(
    viewModel: HomeViewModel
) {
    val tasksList = viewModel.taskInfoList.collectAsState().value
        .sortedWith(compareBy<TasksInfo> { it.status }.thenBy { it.title })

    val openAlertDialog = remember {
        mutableStateOf(false)
    }
    var currentSelectedTask by remember {
        mutableStateOf<TasksInfo?>(null)
    }

    // to delete a selected note.
    if (openAlertDialog.value) {
        OpenDialog(
            onDismissRequest = {
                openAlertDialog.value = false
            },
            onConfirmation = {
                openAlertDialog.value = false
                if (currentSelectedTask != null) {
                    viewModel.deleteTask(currentSelectedTask!!)
                }
            },
            dialogTitle = "Delete",
            dialogText = "Are you sure you want to delete this task permanently?",
            icon = Icons.Default.Delete
        )
    }
    ListHolder(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(top = 8.dp)
    ) {
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
                TextCheckBox(
                    modifier = Modifier
                        .animateItemPlacement(),
                    title = task.title,
                    isChecked = isChecked,
                    shouldStrikeThrough = true,
                    onClicked = {
                        isChecked = !isChecked
                        task.status = !task.status
                        viewModel.setTaskStatus(task)
                    },
                    onLongClicked = {
                        openAlertDialog.value = true
                        currentSelectedTask = task
                    }
                )
            }
        }
    }
}