package com.billion_dollor_company.notesapp.ui.screen.readNote

import android.os.Build
import android.util.Log
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
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditScreenViewModel @Inject constructor(
    private val repository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val uuid = savedStateHandle.get<String>(Constants.Navigation.UUID) ?: ""

    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var pageTitle by mutableStateOf("")

    init {
        Log.d(Constants.DTAG, "uuid is:{$uuid}")
        if (uuid != "") {
            viewModelScope.launch {
                if (uuid.isNotEmpty()) {
                    val note = repository.getNoteByUUID(UUIDConverter().uuidFromString(uuid))
                    title = note.title
                    description = note.description
                }
            }
            pageTitle = "Edit Note"
        } else {
            pageTitle = "Add Note"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addOrUpdateNote() = viewModelScope.launch {
        repository.addOrUpdateNote(
            NoteInfo(
                uid = if (uuid != "")
                    UUIDConverter().uuidFromString(uuid)
                else
                    UUID.randomUUID(),
                title = title,
                description = description
            )
        )
    }
}