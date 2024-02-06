package com.billion_dollor_company.notesapp.ui.screen.tasks.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.billion_dollor_company.notesapp.ui.screen.components.OutlinedInputText
import com.billion_dollor_company.notesapp.ui.screen.components.TextCheckBox
import com.billion_dollor_company.notesapp.util.Constants
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskBottomSheet(
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
                        text = "Add new Task",
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
                // this is used to get focus on the text field
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
                val radioOptions = listOf(
                    Constants.Tasks.Labels.LOW_PRIORITY_LABEL,
                    Constants.Tasks.Labels.MEDIUM_PRIORITY_LABEL,
                    Constants.Tasks.Labels.HIGH_PRIORITY_LABEL,
                )
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
                            Spacer(Modifier.weight(1f))
                            CircleDot(
                                color = when (text) {
                                    Constants.Tasks.Labels.LOW_PRIORITY_LABEL -> {
                                        Constants.Tasks.getColor(Constants.Tasks.LOW_PRIORITY)
                                    }

                                    Constants.Tasks.Labels.MEDIUM_PRIORITY_LABEL -> {
                                        Constants.Tasks.getColor(Constants.Tasks.MEDIUM_PRIORITY)
                                    }

                                    else -> {
                                        Constants.Tasks.getColor(Constants.Tasks.HIGH_PRIORITY)
                                    }
                                }
                            )
                            Spacer(Modifier.width(16.dp ))
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
