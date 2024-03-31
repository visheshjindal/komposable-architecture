package com.toggl.komposable.sample.digibank.data

import kotlinx.serialization.Serializable

@Serializable
data class TransactionDetails(
    val date: String,
    val description: String,
    val amount: Double,
    val currency: String = "USD"
)
