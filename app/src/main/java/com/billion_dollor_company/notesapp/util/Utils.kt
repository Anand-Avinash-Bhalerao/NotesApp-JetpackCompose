package com.billion_dollor_company.notesapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(date: LocalDateTime): String {
    return date.format(DateTimeFormatter.ofPattern("d MMM hh:mm a"))
}