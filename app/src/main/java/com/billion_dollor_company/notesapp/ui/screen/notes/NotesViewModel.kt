package com.billion_dollor_company.notesapp.ui.screen.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.billion_dollor_company.notesapp.model.NoteInfo
import com.billion_dollor_company.notesapp.repository.NoteRepository
import com.billion_dollor_company.notesapp.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val notesStateFlow = MutableStateFlow<List<NoteInfo>>(emptyList())
    val noteInfoList = notesStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.getAllNotes().distinctUntilChanged()
                .collect { listOfNotes ->
                    notesStateFlow.value = listOfNotes
                }
        }
    }

    fun deleteNote(note: NoteInfo) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }
}