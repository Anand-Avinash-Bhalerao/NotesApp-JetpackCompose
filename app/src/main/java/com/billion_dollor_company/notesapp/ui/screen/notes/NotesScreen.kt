package com.billion_dollor_company.notesapp.ui.screen.notes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.billion_dollor_company.notesapp.model.NoteInfo
import com.billion_dollor_company.notesapp.ui.components.EmptyLogo
import com.billion_dollor_company.notesapp.ui.components.NotesInfoCard
import com.billion_dollor_company.notesapp.ui.components.OpenDialog
import com.billion_dollor_company.notesapp.ui.screen.components.CommonScaffold
import com.billion_dollor_company.notesapp.ui.screen.components.ListHolder

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotesScreen(
    viewModel: NotesViewModel = hiltViewModel(),
    addNote: () -> Unit,
    onNoteClicked: (String) -> Unit
) {
    val notesList = viewModel.noteInfoList.collectAsState().value.sortedBy {
        it.entryDate
    }.reversed()
    val openAlertDialog = remember {
        mutableStateOf(false)
    }
    var currentSelectedNote by remember {
        mutableStateOf<NoteInfo?>(null)
    }

    // to delete a selected note.
    if (openAlertDialog.value) {
        OpenDialog(
            onDismissRequest = {
                openAlertDialog.value = false
            },
            onConfirmation = {
                openAlertDialog.value = false
                if (currentSelectedNote != null) {
                    viewModel.deleteNote(currentSelectedNote!!)
                }
            },
            dialogTitle = "Delete",
            dialogText = "Are you sure you want to delete this note permanently?",
            icon = Icons.Default.Delete
        )
    }

    CommonScaffold(
        title = "Notes",
        onFABClick = { addNote() }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (notesList.isNotEmpty()) {
                ListHolder(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .padding(horizontal = 8.dp)
                ) {

                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2),
                        verticalItemSpacing = 8.dp,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxSize(),
                        content = {
                            items(notesList) { note ->
                                NotesInfoCard(
                                    noteInfo = note,
                                    onNoteClicked = onNoteClicked,
                                    onLongClicked = {
                                        openAlertDialog.value = true
                                        currentSelectedNote = note
                                    }
                                )
                            }
                        }
                    )
                }
            } else {
                EmptyLogo()
            }
        }
    }
}