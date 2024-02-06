package com.billion_dollor_company.notesapp.ui.screen.notes.readNote

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    // this could be either "edit note" or "add note"
    var pageTitle by mutableStateOf("")

    //    var category by mutableStateOf("")
    private var _category = MutableStateFlow("")
    val category = _category.asStateFlow()

    var isFavorite by mutableStateOf(false)

    init {
        Log.d(Constants.DTAG, "uuid is:{$uuid}")
        if (uuid != "") {
            viewModelScope.launch {
                if (uuid.isNotEmpty()) {
                    val note = repository.getNoteByUUID(UUIDConverter().uuidFromString(uuid))
                    Log.d(Constants.DTAG, "The note is $note")
                    title = note.title
                    description = note.description
                    _category.value = note.category
                    isFavorite = note.isFavorite
                }
            }
            pageTitle = "Edit Note"
        } else {
            _category.value = Constants.NoteCategories.GENERAL
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
                description = description,
                category = category.value,
                isFavorite = isFavorite,
            )
        )
    }

    fun setCategory(category: String) {
        _category.value = category
    }

    fun getCategory() = _category.value
}