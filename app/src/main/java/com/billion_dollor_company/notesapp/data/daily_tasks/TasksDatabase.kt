package com.billion_dollor_company.notesapp.data.daily_tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.billion_dollor_company.notesapp.model.TasksInfo
import com.billion_dollor_company.notesapp.util.converters.DateConverter
import com.billion_dollor_company.notesapp.util.converters.UUIDConverter

@RequiresApi(Build.VERSION_CODES.O)
@Database(entities = [TasksInfo::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract class TasksDatabase : RoomDatabase(){
    abstract fun taskDAO() : TasksDatabaseDAO
}