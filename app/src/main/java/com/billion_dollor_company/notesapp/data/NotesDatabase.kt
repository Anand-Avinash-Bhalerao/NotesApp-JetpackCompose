package com.billion_dollor_company.notesapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.billion_dollor_company.notesapp.model.NoteInfo
import com.billion_dollor_company.notesapp.util.converters.DateConverter
import com.billion_dollor_company.notesapp.util.converters.UUIDConverter


@RequiresApi(Build.VERSION_CODES.O)
@Database(entities = [NoteInfo::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract class NotesDatabase : RoomDatabase(){
    abstract fun noteDAO() : NoteDatabaseDAO
}