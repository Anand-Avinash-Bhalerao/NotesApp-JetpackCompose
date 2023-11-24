package com.billion_dollor_company.notesapp.repository

import com.billion_dollor_company.notesapp.data.daily_tasks.TasksDatabaseDAO
import com.billion_dollor_company.notesapp.model.TasksInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDatabaseDAO: TasksDatabaseDAO) {

    suspend fun addTask(task: TasksInfo) = taskDatabaseDAO.insert(task)

    suspend fun setTaskStatus(task: TasksInfo) = taskDatabaseDAO.update(task)
    suspend fun deleteTask(task: TasksInfo) = taskDatabaseDAO.delete(task)
    fun getAllTasks(): Flow<List<TasksInfo>> {
        return taskDatabaseDAO.getTasks().flowOn(Dispatchers.IO).conflate()
    }
}