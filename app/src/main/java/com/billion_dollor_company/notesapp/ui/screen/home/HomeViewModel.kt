package com.billion_dollor_company.notesapp.ui.screen.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.billion_dollor_company.notesapp.model.NoteInfo
import com.billion_dollor_company.notesapp.model.TasksInfo
import com.billion_dollor_company.notesapp.repository.NoteRepository
import com.billion_dollor_company.notesapp.repository.TaskRepository
import com.billion_dollor_company.notesapp.util.menu.TabItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val notesStateFlow = MutableStateFlow<List<NoteInfo>>(emptyList())
    val noteInfoList = notesStateFlow.asStateFlow()

    private val tasksStateFlow = MutableStateFlow<List<TasksInfo>>(emptyList())
    val taskInfoList = tasksStateFlow.asStateFlow()

    val tabItems = listOf(
        TabItem(
            title = "Tasks",
            unselectedIcon = Icons.Outlined.Checklist,
            selectedIcon = Icons.Filled.Checklist,
        ),
        TabItem(
            title = "Notes",
            unselectedIcon = Icons.Outlined.Notes,
            selectedIcon = Icons.Filled.Notes,
        )
    )
    var selectedTabIndex by mutableIntStateOf(0)


    init {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.getAllNotes().distinctUntilChanged()
                .collect { listOfNotes ->
                    notesStateFlow.value = listOfNotes
                }
        }

        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.getAllTasks().distinctUntilChanged()
                .collect { listOfTasks ->
                    tasksStateFlow.value = listOfTasks

                }

        }
    }


    fun deleteNote(note: NoteInfo) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }

    private fun getAllTasks() = viewModelScope.launch {
        taskRepository.getAllTasks().distinctUntilChanged()
            .collect { listOfTasks ->
                tasksStateFlow.value = listOfTasks
            }
    }

    fun addTask(task: TasksInfo) = viewModelScope.launch {
        taskRepository.addTask(task)
    }

    fun setTaskStatus(task: TasksInfo) = viewModelScope.launch {
        taskRepository.setTaskStatus(task)
    }

    fun deleteTask(task: TasksInfo) = viewModelScope.launch {
        taskRepository.deleteTask(task)
    }

}