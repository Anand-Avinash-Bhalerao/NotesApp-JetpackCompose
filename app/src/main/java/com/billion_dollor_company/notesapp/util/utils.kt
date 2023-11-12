package com.billion_dollor_company.notesapp.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(time: Long): String {
    val date = Date(time)
    val dateFormat = SimpleDateFormat(
        "d MMM hh:mm aaa",
        Locale.getDefault()
    )
    return dateFormat.format(date)
}