package com.billion_dollor_company.notesapp.repository

import androidx.room.Query
import com.billion_dollor_company.notesapp.data.checklist.ChecklistDatabaseDAO
import com.billion_dollor_company.notesapp.model.ChecklistInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ChecklistRepository @Inject constructor(private val checklistDatabaseDAO: ChecklistDatabaseDAO) {

    suspend fun addItem(checklistInfo: ChecklistInfo) = checklistDatabaseDAO.insert(checklistInfo)

    suspend fun setBuyItemStatus(checklistInfo: ChecklistInfo) =
        checklistDatabaseDAO.update(checklistInfo)

    suspend fun deleteItem(checklistInfo: ChecklistInfo) =
        checklistDatabaseDAO.delete(checklistInfo)

    suspend fun deleteCompletedItems() = checklistDatabaseDAO.deleteCompletedItems()

    fun getAllItems(): Flow<List<ChecklistInfo>> {
        return checklistDatabaseDAO.getAllChecklist().flowOn(Dispatchers.IO).conflate()
    }
}