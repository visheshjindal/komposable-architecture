package com.toggl.komposable.sample.digibank.transactions

import com.toggl.komposable.architecture.ReduceResult
import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.extensions.withFlowEffect
import com.toggl.komposable.extensions.withoutEffect
import com.toggl.komposable.sample.digibank.data.datasource.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TransactionsReducer: Reducer<TransactionsUIState, TransactionsAction> {
    override fun reduce(
        state: TransactionsUIState,
        action: TransactionsAction
    ): ReduceResult<TransactionsUIState, TransactionsAction> {
        return when (action) {
            is TransactionsAction.LoadTransactions -> {
                state.copy(isLoading = true).withFlowEffect(
                    flow {
                        RepositoryImpl.fetchTransactions().fold(
                            onSuccess = {
                                emit(TransactionsAction.OnTransactionsLoadedSuccessful(it))
                            },
                            onFailure = {
                                emit(TransactionsAction.OnTransactionsLoadedFailed(it))
                            }
                        )
                    }.flowOn(Dispatchers.IO))
            }

            is TransactionsAction.OnTransactionsLoadedFailed -> {
                state.copy(throwable = action.error, isLoading = false).withoutEffect()
            }

            is TransactionsAction.OnTransactionsLoadedSuccessful -> {
                state.copy(transactions = action.transactions, isLoading = false)
                    .withoutEffect()
            }
        }
    }
}