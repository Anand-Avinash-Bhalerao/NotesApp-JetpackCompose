package com.billion_dollor_company.notesapp.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID

@Entity(tableName = "notes_table")
@RequiresApi(Build.VERSION_CODES.O)
data class NoteInfo(
    @PrimaryKey
    val uid: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "date")
    val entryDate : LocalDateTime = LocalDateTime.now()
)