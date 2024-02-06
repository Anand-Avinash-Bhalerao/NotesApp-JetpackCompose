package com.billion_dollor_company.notesapp.util

import androidx.compose.foundation.MutatePriority
import androidx.compose.ui.graphics.Color
import com.billion_dollor_company.notesapp.ui.theme.highPriority
import com.billion_dollor_company.notesapp.ui.theme.lowPriority
import com.billion_dollor_company.notesapp.ui.theme.mediumPriority

object Constants {
    const val DTAG = "debugging_tag"

    object Screens {
        const val TASK_PAGE = 0
        const val NOTES_PAGE = 1
    }

    object Tasks {
        object Labels {
            const val PAGE_LABEL = "Tasks"
            const val LOW_PRIORITY_LABEL = "Low"
            const val MEDIUM_PRIORITY_LABEL = "Medium"
            const val HIGH_PRIORITY_LABEL = "High"
        }

        const val COMPLETED = true
        const val NOT_COMPLETED = false

        const val HIGH_PRIORITY = 1
        const val MEDIUM_PRIORITY = 2
        const val LOW_PRIORITY = 3

        fun getColor(priority: Int): Color {
            return when (priority) {
                LOW_PRIORITY -> lowPriority
                MEDIUM_PRIORITY -> mediumPriority
                HIGH_PRIORITY -> highPriority
                else -> lowPriority
            }
        }

    }

    object NoteCategories {
        const val ALL = "All"
        const val FAVORITES = "Favorites"
        const val GENERAL = "General"
        const val PROJECTS = "Projects"
        const val WORK = "Work"
        const val PERSONAL = "Personal"
        const val CREDENTIALS = "Credentials"
    }

    object ToBuy {
        const val COMPLETED = true
        const val NOT_COMPLETED = false
    }

    object Navigation {
        const val UUID = "uuid"
    }
}