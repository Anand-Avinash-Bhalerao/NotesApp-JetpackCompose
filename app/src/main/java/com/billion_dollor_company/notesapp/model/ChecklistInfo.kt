package com.billion_dollor_company.notesapp.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.billion_dollor_company.notesapp.util.Constants
import java.util.UUID


@Entity(tableName = "checklist_table")
@RequiresApi(Build.VERSION_CODES.O)
data class ChecklistInfo(
    @PrimaryKey
    val uid: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "title")
    val name: String,

    @ColumnInfo(name = "status")
    var status: Boolean = Constants.ToBuy.NOT_COMPLETED,
)