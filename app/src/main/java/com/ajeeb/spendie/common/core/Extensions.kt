package com.ajeeb.spendie.common.core

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Date.parseDate(format: String): String {
    val cal = Calendar.getInstance().apply { time = this@parseDate }
    // Define the desired date format
    val formattedDate = SimpleDateFormat(format, Locale.getDefault()).format(cal.time)
    return formattedDate
}