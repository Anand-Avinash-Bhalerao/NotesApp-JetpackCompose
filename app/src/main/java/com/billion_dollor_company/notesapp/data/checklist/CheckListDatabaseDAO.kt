package com.billion_dollor_company.notesapp.data.checklist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.billion_dollor_company.notesapp.model.CheckListInfo
import com.billion_dollor_company.notesapp.model.TasksInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface CheckListDatabaseDAO {
    @Query("SELECT * FROM checklist_table")
    fun getAllCheckLists(): Flow<List<CheckListInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(info: CheckListInfo)

    @Delete
    suspend fun delete(info: CheckListInfo)
}
