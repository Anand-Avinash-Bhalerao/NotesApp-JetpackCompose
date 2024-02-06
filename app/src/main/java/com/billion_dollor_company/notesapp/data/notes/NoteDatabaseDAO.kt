package com.billion_dollor_company.notesapp.data.notes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.billion_dollor_company.notesapp.model.NoteInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDatabaseDAO {

    @Query("SELECT * FROM notes_table")
    fun getNotes() : Flow<List<NoteInfo>>

    @Query("SELECT * FROM notes_table where uid =:id")
    suspend fun getNoteById(id : String) : NoteInfo

    @Query("SELECT * FROM notes_table where category =:category")
    fun getAllNotesOfCategory(category : String) : Flow<List<NoteInfo>>

    @Query("SELECT * FROM notes_table where isFavorite = 1")
    fun getAllFavorites() : Flow<List<NoteInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteInfo)

    @Delete
    suspend fun delete(note : NoteInfo)
}