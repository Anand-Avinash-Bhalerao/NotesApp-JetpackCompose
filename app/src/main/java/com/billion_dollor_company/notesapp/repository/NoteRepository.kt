package com.billion_dollor_company.notesapp.repository

import com.billion_dollor_company.notesapp.data.NoteDatabaseDAO
import com.billion_dollor_company.notesapp.model.NoteInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDatabaseDAO: NoteDatabaseDAO) {

    suspend fun addNote(note: NoteInfo) = noteDatabaseDAO.insert(note)
    suspend fun updateNote(note: NoteInfo) = noteDatabaseDAO.update(note)
    suspend fun deleteNote(note: NoteInfo) = noteDatabaseDAO.delete(note)
    fun getAllNotes(): Flow<List<NoteInfo>> =
        noteDatabaseDAO.getNotes().flowOn(Dispatchers.IO).conflate()

}