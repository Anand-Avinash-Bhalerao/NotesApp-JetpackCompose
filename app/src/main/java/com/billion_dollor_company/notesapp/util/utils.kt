package com.billion_dollor_company.notesapp.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(time: Long): String {
    val date = Date(time)
    val dateFormat = SimpleDateFormat(
//        "EEE, d MMM hh:mm aaa",
        "d MMM hh:mm",
        Locale.getDefault()
    )
    return dateFormat.format(date)
}