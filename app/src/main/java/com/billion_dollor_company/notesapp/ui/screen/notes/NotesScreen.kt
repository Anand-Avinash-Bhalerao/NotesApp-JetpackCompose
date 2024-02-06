package com.billion_dollor_company.notesapp.ui.screen.notes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.NoteAdd
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.billion_dollor_company.notesapp.model.NoteInfo
import com.billion_dollor_company.notesapp.ui.screen.components.EmptyLogo
import com.billion_dollor_company.notesapp.ui.screen.notes.components.NotesInfoCard
import com.billion_dollor_company.notesapp.ui.screen.components.ConfirmationDialog
import com.billion_dollor_company.notesapp.ui.screen.components.CommonScaffold
import com.billion_dollor_company.notesapp.ui.screen.components.ListHolder
import com.billion_dollor_company.notesapp.util.Constants

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
    var curCategory = viewModel.category.collectAsState().value

    val openAlertDialog = remember {
        mutableStateOf(false)
    }
    var currentSelectedNote by remember {
        mutableStateOf<NoteInfo?>(null)
    }

    // to delete a selected note.
    if (openAlertDialog.value) {
        ConfirmationDialog(
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
        onFloatingButtonClick = { addNote() },
        floatingButtonIcon = Icons.Outlined.NoteAdd,
        actionButton = {
            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Add to favorite"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ListHolder {
                var categoryInfoList = mutableListOf(
                    Constants.NoteCategories.PERSONAL,
                    Constants.NoteCategories.PROJECTS,
                    Constants.NoteCategories.WORK,
                    Constants.NoteCategories.CREDENTIALS
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {

                    val allLabel = Constants.NoteCategories.ALL
                    FilterChip(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        selected = curCategory == allLabel,
                        onClick = {
                            curCategory = allLabel
                            viewModel.setCategory(allLabel)
                            viewModel.getAllNotes()
                        },
                        label = { Text(allLabel) },
                        leadingIcon = if (curCategory == allLabel) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = "Localized Description",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        }
                    )

                    val favLabel = Constants.NoteCategories.FAVORITES
                    FilterChip(
                        modifier = Modifier.padding(end = 8.dp),
                        selected = curCategory == favLabel,
                        onClick = {
                            curCategory = favLabel
                            viewModel.setCategory(favLabel)
                            viewModel.getAllFavorites()
                        },
                        label = { Text(favLabel) },
                        leadingIcon = if (curCategory == favLabel) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = "Localized Description",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        }
                    )


                    for (categoryLabel in categoryInfoList) {
                        FilterChip(
                            modifier = Modifier.padding(end = 8.dp),
                            selected = curCategory == categoryLabel,
                            onClick = {
                                curCategory = categoryLabel
                                viewModel.setCategory(categoryLabel)
                                viewModel.getNoteByCategory(categoryLabel)
                            },
                            label = { Text(categoryLabel) },
                            leadingIcon = if (curCategory == categoryLabel) {
                                {
                                    Icon(
                                        imageVector = Icons.Filled.Done,
                                        contentDescription = "Localized Description",
                                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                                    )
                                }
                            } else {
                                null
                            }
                        )
                    }
                }
                if (notesList.isNotEmpty()) {


                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2),
                        verticalItemSpacing = 10.dp,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(10.dp),
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
                } else {
                    EmptyLogo(
                        displayedString = "No note found!"
                    )
                }
            }

        }
    }
}