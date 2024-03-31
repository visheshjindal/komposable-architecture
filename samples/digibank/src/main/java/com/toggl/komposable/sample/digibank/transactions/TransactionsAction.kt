package com.toggl.komposable.sample.digibank.transactions

import com.toggl.komposable.sample.digibank.data.TransactionDetails

sealed class TransactionsAction {
    data object LoadTransactions : TransactionsAction()
    data class OnTransactionsLoadedSuccessful(val transactions: List<TransactionDetails>) : TransactionsAction()
    data class OnTransactionsLoadedFailed(val error: Throwable) : TransactionsAction()
}