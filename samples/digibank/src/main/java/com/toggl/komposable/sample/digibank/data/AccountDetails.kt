package com.toggl.komposable.sample.digibank.data

data class AccountDetails(
    val accountNumber: String = "",
    val accountType: String = "",
    val balance: Double = 0.0,
    val currency: String = ""
)

