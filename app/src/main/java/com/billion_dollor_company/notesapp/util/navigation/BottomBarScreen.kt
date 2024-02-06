package com.billion_dollor_company.notesapp.util.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.StickyNote2
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.StickyNote2
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon : ImageVector
) {
    object Tasks : BottomBarScreen(
        route = "Tasks",
        title = "Tasks",
        unselectedIcon = Icons.Outlined.WorkOutline,
        selectedIcon = Icons.Filled.Work
    )

    object Notes : BottomBarScreen(
        route = "Notes",
        title = "Notes",
        unselectedIcon = Icons.Outlined.StickyNote2,
        selectedIcon = Icons.Filled.StickyNote2
    )

    object Checklist : BottomBarScreen(
        route = "Checklist",
        title = "Checklist",
        unselectedIcon = Icons.Outlined.Checklist,
        selectedIcon = Icons.Filled.Checklist
    )
}