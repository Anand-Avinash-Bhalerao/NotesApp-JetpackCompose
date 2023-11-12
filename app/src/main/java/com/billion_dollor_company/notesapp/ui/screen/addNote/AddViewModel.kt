package com.billion_dollor_company.notesapp.ui.screen.addNote

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.billion_dollor_company.notesapp.model.NoteInfo
import com.billion_dollor_company.notesapp.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    var title by mutableStateOf("")
    var description by mutableStateOf("")

    @RequiresApi(Build.VERSION_CODES.O)
    fun addNote() {
        viewModelScope.launch {
            repository.addNote(
                NoteInfo(
                    title = title,
                    description = description
                )
            )
        }
    }
}