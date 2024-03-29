package com.billion_dollor_company.notesapp.repository

import com.billion_dollor_company.notesapp.data.notes.NoteDatabaseDAO
import com.billion_dollor_company.notesapp.model.NoteInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDatabaseDAO: NoteDatabaseDAO) {

    suspend fun addOrUpdateNote(note: NoteInfo) = noteDatabaseDAO.insert(note)
    suspend fun deleteNote(note: NoteInfo) = noteDatabaseDAO.delete(note)
    suspend fun getAllNotes(): Flow<List<NoteInfo>> {
        return noteDatabaseDAO.getNotes().flowOn(Dispatchers.IO).conflate()
    }

    suspend fun getAllNotesOfCategory(category: String): Flow<List<NoteInfo>> {
        return noteDatabaseDAO.getAllNotesOfCategory(category).flowOn(Dispatchers.IO).conflate()
    }

    suspend fun getAllFavoriteNotes(): Flow<List<NoteInfo>> {
        return noteDatabaseDAO.getAllFavorites().flowOn(Dispatchers.IO).conflate()
    }

    suspend fun getNoteByUUID(uuid: UUID): NoteInfo =
        noteDatabaseDAO.getNoteById(uuid.toString())
}