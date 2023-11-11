package com.billion_dollor_company.notesapp.ui.screen.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.billion_dollor_company.notesapp.model.NoteInfo
import com.billion_dollor_company.notesapp.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {


    private val _noteInfoList = MutableStateFlow<List<NoteInfo>>(emptyList())
    val noteInfoList = _noteInfoList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNotes().distinctUntilChanged()
                .collect{listOfNotes->
                    _noteInfoList.value = listOfNotes

                }
        }
    }

    fun addNote(note: NoteInfo) = viewModelScope.launch {
        repository.addNote(note)
    }

    fun updateNote(note: NoteInfo) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun deleteNote(note: NoteInfo) = viewModelScope.launch {
        repository.deleteNote(note)
    }

}