package com.billion_dollor_company.notesapp.data.checklist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.billion_dollor_company.notesapp.model.ChecklistInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface ChecklistDatabaseDAO {

    @Query("SELECT * FROM checklist_table")
    fun getAllChecklist() : Flow<List<ChecklistInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(checklistInfo: ChecklistInfo)

    @Delete
    suspend fun delete(checklistInfo : ChecklistInfo)

    @Update
    suspend fun update(checklistInfo : ChecklistInfo)
}