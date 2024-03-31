package com.toggl.komposable.sample.digibank.transactions

import com.toggl.komposable.sample.digibank.data.datasource.Repository
import com.toggl.komposable.sample.digibank.data.datasource.RepositoryImpl
import com.toggl.komposable.utils.SuspendEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadTransactionsEffect(private val repository: Repository = RepositoryImpl): SuspendEffect<TransactionsAction>() {
    override suspend fun execute(): TransactionsAction? = withContext(Dispatchers.IO) {
        repository.fetchTransactions().fold(
            onSuccess = { transactions ->
                TransactionsAction.OnTransactionsLoadedSuccessful(transactions)
            },
            onFailure = { error ->
                TransactionsAction.OnTransactionsLoadedFailed(error)
            }
        )
    }
}