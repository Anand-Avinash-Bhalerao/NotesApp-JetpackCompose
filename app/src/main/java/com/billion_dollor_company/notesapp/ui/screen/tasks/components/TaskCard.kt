package com.billion_dollor_company.notesapp.ui.screen.tasks.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.billion_dollor_company.notesapp.util.Constants


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    title: String,
    isChecked: Boolean,
    shouldStrikeThrough: Boolean = false,
    onLongClicked: () -> Unit = {},
    taskPriority: Int = Constants.Tasks.LOW_PRIORITY,
    onClicked: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .toggleable(
                value = false,
                onValueChange = onClicked,
                role = Role.Checkbox
            )
            .combinedClickable(
                onClick = {
                    onClicked(false)
                },
                onLongClick = {
                    onLongClicked()
                }
            )
            .padding(16.dp),
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = null
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(
                textDecoration = if (shouldStrikeThrough and isChecked) TextDecoration.LineThrough else TextDecoration.None,
                color = if (shouldStrikeThrough and isChecked) Color.DarkGray else MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        )
        CircleDot(
            color = if (!isChecked) Constants.Tasks.getColor(taskPriority)
            else Color.Transparent,
        )

    }
}