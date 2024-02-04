package com.billion_dollor_company.notesapp.data.grocery

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.billion_dollor_company.notesapp.model.ToBuyInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToBuyDatabaseDAO {

    @Query("SELECT * FROM to_buy_table")
    fun getAllToBuy() : Flow<List<ToBuyInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(toBuyInfo: ToBuyInfo)

    @Delete
    suspend fun delete(toBuyInfo : ToBuyInfo)

    @Update
    suspend fun update(toBuyInfo : ToBuyInfo)
}