package com.billion_dollor_company.notesapp.data.grocery

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.billion_dollor_company.notesapp.model.ToBuyInfo
import com.billion_dollor_company.notesapp.util.converters.UUIDConverter


@RequiresApi(Build.VERSION_CODES.O)
@Database(entities = [ToBuyInfo::class], version = 1, exportSchema = false)
@TypeConverters(UUIDConverter::class)
abstract class ToBuyDatabase : RoomDatabase(){
    abstract fun toBuyDAO() : ToBuyDatabaseDAO
}