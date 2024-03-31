package com.toggl.komposable.sample.digibank.extenstions

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun Double.toCommaSeparatedString(): String {
    val formatter = NumberFormat.getNumberInstance(Locale.US)
    return formatter.format(this)
}

fun String.toFormattedDateString(): String {
    val parser = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val formatter = SimpleDateFormat("dd MMM, yyyy", Locale.US)
    val date = parser.parse(this)
    return date?.let { formatter.format(it) } ?: this
}