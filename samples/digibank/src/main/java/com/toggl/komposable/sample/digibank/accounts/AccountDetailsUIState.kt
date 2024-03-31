package com.toggl.komposable.sample.digibank.accounts

import com.toggl.komposable.sample.digibank.data.AccountDetails

data class AccountDetailsUIState(
    val accountDetails: AccountDetails = AccountDetails(),
    val throwable: Throwable? = null,
    val isLoading: Boolean = false,
)
