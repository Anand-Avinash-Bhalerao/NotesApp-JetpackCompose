package com.billion_dollor_company.notesapp.data.daily_tasks

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.billion_dollor_company.notesapp.model.TasksInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDatabaseDAO {

    @Query("SELECT * FROM tasks_table")
    fun getTasks() : Flow<List<TasksInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TasksInfo)

    @Delete
    suspend fun delete(task : TasksInfo)

    @Update
    suspend fun update(task : TasksInfo)
}