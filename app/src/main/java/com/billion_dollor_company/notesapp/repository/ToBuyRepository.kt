package com.billion_dollor_company.notesapp.repository

import com.billion_dollor_company.notesapp.data.grocery.ToBuyDatabaseDAO
import com.billion_dollor_company.notesapp.model.ToBuyInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ToBuyRepository @Inject constructor(private val toBuyDatabaseDAO: ToBuyDatabaseDAO) {

    suspend fun addItem(toBuyInfo: ToBuyInfo) = toBuyDatabaseDAO.insert(toBuyInfo)

    suspend fun setBuyItemStatus(toBuyInfo: ToBuyInfo) = toBuyDatabaseDAO.update(toBuyInfo)
    suspend fun deleteItem(toBuyInfo: ToBuyInfo) = toBuyDatabaseDAO.delete(toBuyInfo)
    fun getAllBuyItems(): Flow<List<ToBuyInfo>> {
        return toBuyDatabaseDAO.getAllToBuy().flowOn(Dispatchers.IO).conflate()
    }
}