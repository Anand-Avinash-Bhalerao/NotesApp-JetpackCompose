package com.billion_dollor_company.notesapp.util.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Tasks : BottomBarScreen(
        route = "Tasks",
        title = "Tasks",
        icon = Icons.Default.Work
    )

    object Notes : BottomBarScreen(
        route = "Notes",
        title = "Notes",
        icon = Icons.Default.Notes
    )

    object Checklist : BottomBarScreen(
        route = "Checklist",
        title = "Checklist",
        icon = Icons.Default.Checklist
    )
}