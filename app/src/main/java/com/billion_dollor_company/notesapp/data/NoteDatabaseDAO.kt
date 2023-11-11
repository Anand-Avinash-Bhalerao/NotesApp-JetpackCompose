package com.billion_dollor_company.notesapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.billion_dollor_company.notesapp.model.NoteInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDatabaseDAO {

    @Query("SELECT * FROM notes_table")
    fun getNotes() : Flow<List<NoteInfo>>

    @Query("SELECT * FROM notes_table where uid =:id")
    suspend fun getNoteById(id : String) : NoteInfo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteInfo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note : NoteInfo)

    @Delete
    suspend fun delete(note : NoteInfo)
}