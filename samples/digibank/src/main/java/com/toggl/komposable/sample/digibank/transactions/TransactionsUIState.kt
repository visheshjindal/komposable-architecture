package com.toggl.komposable.sample.digibank.transactions

import com.toggl.komposable.sample.digibank.data.TransactionDetails

data class TransactionsUIState(
    val transactions: List<TransactionDetails> = emptyList(),
    val throwable: Throwable? = null,
    val isLoading: Boolean = false,
)
