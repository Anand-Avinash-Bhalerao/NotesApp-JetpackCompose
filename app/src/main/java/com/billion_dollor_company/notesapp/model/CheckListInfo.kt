package com.billion_dollor_company.notesapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.billion_dollor_company.notesapp.util.Constants
import java.util.UUID

@Entity(tableName = "checklist_table")
data class CheckListInfo(
    @PrimaryKey
    val uid: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "status")
    var status: Boolean = Constants.Tasks.NOT_COMPLETED
)