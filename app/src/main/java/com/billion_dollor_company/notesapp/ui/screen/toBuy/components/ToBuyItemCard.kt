package com.billion_dollor_company.notesapp.ui.screen.toBuy.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToBuyItemCard(
    modifier: Modifier = Modifier,
    title: String,
    quantity: String,
    isChecked: Boolean,
    shouldStrikeThrough: Boolean = false,
    onLongClicked: () -> Unit = {},
    onClicked: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier
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
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = null
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = if (shouldStrikeThrough and isChecked) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (shouldStrikeThrough and isChecked) Color.DarkGray else MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            if (quantity != "") {
                Text(
                    text = "Quantity: $quantity",
                    style = MaterialTheme.typography.bodySmall.copy(
                        textDecoration = if (shouldStrikeThrough and isChecked) TextDecoration.LineThrough else TextDecoration.None,
                        color = if (shouldStrikeThrough and isChecked) Color.DarkGray else MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}
