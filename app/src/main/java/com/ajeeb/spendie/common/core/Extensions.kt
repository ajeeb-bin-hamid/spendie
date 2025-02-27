package com.ajeeb.spendie.common.core

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


fun Date.toUtcString(): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    format.timeZone = TimeZone.getTimeZone("UTC") // Set to UTC
    return format.format(this)
}

fun String?.parseDate(format: String): String {
    val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

    val date: Date? = if (this != null) {
        isoFormat.parse(this)
    } else {
        null
    }

    return if (date != null) {
        SimpleDateFormat(format, Locale.getDefault()).format(date)
    } else {
        ""
    }
}