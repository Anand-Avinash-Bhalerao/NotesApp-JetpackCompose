package com.billion_dollor_company.notesapp.ui.screen.tasks

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
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.billion_dollor_company.notesapp.model.TasksInfo
import com.billion_dollor_company.notesapp.ui.components.TextCheckBox
import com.billion_dollor_company.notesapp.ui.screen.components.OpenDialog
import com.billion_dollor_company.notesapp.ui.screen.components.OutlinedInputText
import com.billion_dollor_company.notesapp.ui.screen.tasks.components.TaskCard
import com.billion_dollor_company.notesapp.ui.screen.components.TextCheckBox
import com.billion_dollor_company.notesapp.ui.screen.components.CommonScaffold
import com.billion_dollor_company.notesapp.ui.screen.components.ListHolder
import com.billion_dollor_company.notesapp.util.Constants
import java.time.LocalDateTime

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskScreen(
    viewModel: TasksViewModel
) {
    val tasksList = viewModel.taskInfoList.collectAsState().value
        .sortedWith(compareBy<TasksInfo> { it.status }.thenBy { it.priority }.thenBy { it.title })

    var openAlertDialog by remember {
        mutableStateOf(false)
    }
    var currentSelectedTask by remember {
        mutableStateOf<TasksInfo?>(null)
    }

    // to delete a selected note.
    if (openAlertDialog) {
        OpenDialog(
            onDismissRequest = {
                openAlertDialog = false
            },
            onConfirmation = {
                openAlertDialog = false
                if (currentSelectedTask != null) {
                    viewModel.deleteTask(currentSelectedTask!!)
                }
            },
            dialogTitle = "Delete",
            dialogText = "Are you sure you want to delete this task permanently?",
            icon = Icons.Default.Delete
        )
    }

    //variables for opening add tasks bottom sheet.
    var openTaskBottomSheet by remember {
        mutableStateOf(false)
    }
    if (openTaskBottomSheet) {
        AddTask(
            onDismiss = {
                openTaskBottomSheet = false
            },
            onAccept = { task ->
                viewModel.addTask(task)
                openTaskBottomSheet = false
            }
        )
    }

    CommonScaffold(
        title = "My Tasks",
        onFABClick = {
            openTaskBottomSheet = true
        }
    ) { paddingValues ->
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
                            openAlertDialog = true
                            currentSelectedTask = task
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
fun AddTask(
    onDismiss: () -> Unit,
    onAccept: (TasksInfo) -> Unit
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
                        text = "Add new task",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        ) {
            var currentText by remember {
                mutableStateOf("")
            }
            var isMarkedForTomorrow by remember {
                mutableStateOf(false)
            }
            Column {

                val focusRequester = remember {
                    FocusRequester()
                }
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }

                OutlinedInputText(
                    text = currentText,
                    placeHolder = "Enter text here",
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .padding(top = 16.dp, bottom = 4.dp)
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) { newText ->
                    currentText = newText
                }
                TextCheckBox(
                    title = "Set this task for tomorrow",
                    isChecked = isMarkedForTomorrow,
                    shouldStrikeThrough = false,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    isMarkedForTomorrow = !isMarkedForTomorrow
                }

                Text(
                    text = "Priority:",
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
                val radioOptions = listOf("Low", "Medium", "High")
                var selectedOption by remember {
                    mutableStateOf(radioOptions[0])
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                ) {
                    radioOptions.forEach { text ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = {
                                        selectedOption = text
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(start = 16.dp, top = 6.dp, bottom = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = null
                            )
                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                    }
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
                            contentDescription = "Delete task button icon"
                        )
                        Spacer(
                            modifier = Modifier.size(ButtonDefaults.IconSpacing)
                        )
                        Text(text = "Discard")
                    }
                    Button(
                        onClick = {
                            onAccept(
                                TasksInfo(
                                    title = currentText,
                                    entryDate = if (isMarkedForTomorrow) {
                                        LocalDateTime.now().plusDays(1)
                                    } else {
                                        LocalDateTime.now()
                                    },
                                    priority =
                                    when (selectedOption) {
                                        radioOptions[0] -> Constants.Tasks.LOW_PRIORITY
                                        radioOptions[1] -> Constants.Tasks.MEDIUM_PRIORITY
                                        else -> Constants.Tasks.HIGH_PRIORITY
                                    }
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
