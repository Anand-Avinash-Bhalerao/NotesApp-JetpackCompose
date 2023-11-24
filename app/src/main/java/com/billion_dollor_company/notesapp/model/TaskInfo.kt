package com.billion_dollor_company.notesapp.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.unit.Constraints
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.billion_dollor_company.notesapp.util.Constants
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID

@Entity(tableName = "tasks_table")
@RequiresApi(Build.VERSION_CODES.O)
data class TasksInfo(
    @PrimaryKey
    val uid: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "tasks_title")
    val title: String,

    @ColumnInfo(name = "tasks_status")
    var status: Boolean = Constants.Tasks.NOT_COMPLETED,

    @ColumnInfo(name = "tasks_date")
    val entryDate : LocalDateTime = LocalDateTime.now()

    )