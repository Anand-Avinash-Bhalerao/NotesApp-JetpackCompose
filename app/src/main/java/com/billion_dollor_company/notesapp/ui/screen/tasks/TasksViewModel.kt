package com.billion_dollor_company.notesapp.ui.screen.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.billion_dollor_company.notesapp.model.TasksInfo
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
@RequiresApi(Build.VERSION_CODES.O)
class TasksViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val tasksStateFlow = MutableStateFlow<List<TasksInfo>>(emptyList())
    val taskInfoList = tasksStateFlow.asStateFlow()

    init {

        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.getAllTasks().distinctUntilChanged()
                .collect { listOfTasks ->
                    tasksStateFlow.value = listOfTasks

                }

        }
    }

    fun addTask(task: TasksInfo) = viewModelScope.launch {
        taskRepository.addTask(task)
    }

    fun setTaskStatus(task: TasksInfo) = viewModelScope.launch {

        task.status = !task.status
        taskRepository.addTask(task)
    }

    fun deleteTask(task: TasksInfo) = viewModelScope.launch {
        taskRepository.deleteTask(task)
    }

}