package com.toggl.komposable.sample.digibank.data

import kotlinx.serialization.Serializable

@Serializable
data class AccountDetails(
    val accountNumber: String = "",
    val accountType: String = "",
    val balance: Double = 0.0,
    val currency: String = "USD"
)

