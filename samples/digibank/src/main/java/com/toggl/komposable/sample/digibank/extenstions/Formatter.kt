package com.toggl.komposable.sample.digibank.extenstions

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Currency
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

// Edit function to return with currency if showCurrency is true
fun Double.toFormattedCurrencyString(currencyCode: String, showCurrency: Boolean): String {
    return if (showCurrency) "${convertCurrencyCodeToSymbol(currencyCode)}${this.toCommaSeparatedString()}"
    else
        "$currencyCode ${this.toCommaSeparatedString()}"
}

fun convertCurrencyCodeToSymbol(currencyCode: String, locale: Locale = Locale.US): String {
    val currency = Currency.getInstance(currencyCode)
    return currency.getSymbol(locale)
}