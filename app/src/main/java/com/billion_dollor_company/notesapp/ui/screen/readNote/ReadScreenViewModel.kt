package com.billion_dollor_company.notesapp.ui.screen.readNote

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.billion_dollor_company.notesapp.model.NoteInfo
import com.billion_dollor_company.notesapp.repository.NoteRepository
import com.billion_dollor_company.notesapp.util.Constants
import com.billion_dollor_company.notesapp.util.converters.UUIDConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadScreenViewModel @Inject constructor(
    private val repository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val uuid = savedStateHandle.get<String>(Constants.Navigation.UUID) ?: ""
    var title by mutableStateOf("")
    var description by mutableStateOf("")

    init {
        viewModelScope.launch {
            if (uuid.isNotEmpty()) {
                val note = repository.getNoteByUUID(UUIDConverter().uuidFromString(uuid))
                title = note.title
                description = note.description
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateNote() = viewModelScope.launch {
        repository.updateNote(
            NoteInfo(
                uid = UUIDConverter().uuidFromString(uuid),
                title = title,
                description = description
            )
        )
    }
}