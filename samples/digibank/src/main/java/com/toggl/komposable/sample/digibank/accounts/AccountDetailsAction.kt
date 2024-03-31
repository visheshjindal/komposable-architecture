package com.toggl.komposable.sample.digibank.accounts

import com.toggl.komposable.sample.digibank.data.AccountDetails

sealed class AccountDetailsAction {
    data object LoadAccountDetails : AccountDetailsAction()
    data class OnAccountDetailsLoadedSuccessful(val accountDetails: AccountDetails) : AccountDetailsAction()
    data class OnAccountDetailsLoadedFailed(val error: Throwable) : AccountDetailsAction()
}