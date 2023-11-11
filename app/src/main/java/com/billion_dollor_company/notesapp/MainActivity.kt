package com.billion_dollor_company.notesapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.billion_dollor_company.notesapp.ui.screen.NoteScreen
import com.billion_dollor_company.notesapp.ui.screen.NoteViewModel
import com.billion_dollor_company.notesapp.ui.theme.NotesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                val noteViewModel : NoteViewModel by viewModels()
                NotesApp(noteViewModel)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotesApp(notesViewModel: NoteViewModel) {

    val notesList = notesViewModel.noteInfoList.collectAsState().value

    NoteScreen(
        notesList = notesList,
        onAddNote = { note ->
            notesViewModel.addNote(note)
        },
        onDeleteNote = { note ->
            notesViewModel.deleteNote(note)
        }
    )
}
