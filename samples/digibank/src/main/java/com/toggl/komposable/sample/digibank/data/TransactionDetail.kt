package com.toggl.komposable.sample.digibank.data

data class TransactionDetails(
    val date: String,
    val description: String,
    val amount: Double,
    val currency: String
)
