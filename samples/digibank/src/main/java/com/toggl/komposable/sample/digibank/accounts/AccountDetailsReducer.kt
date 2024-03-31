package com.toggl.komposable.sample.digibank.accounts

import com.toggl.komposable.architecture.ReduceResult
import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.extensions.withFlowEffect
import com.toggl.komposable.extensions.withoutEffect
import com.toggl.komposable.sample.digibank.data.datasource.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AccountDetailsReducer: Reducer<AccountDetailsUIState, AccountDetailsAction>{
    override fun reduce(
        state: AccountDetailsUIState,
        action: AccountDetailsAction
    ): ReduceResult<AccountDetailsUIState, AccountDetailsAction> {
        return when (action) {
            is AccountDetailsAction.LoadAccountDetails -> {
                state.copy(isLoading = true).withFlowEffect(
                    flow {
                        RepositoryImpl.fetchAccount().fold(
                            onSuccess = {
                                emit(AccountDetailsAction.OnAccountDetailsLoadedSuccessful(it))
                            },
                            onFailure = {
                                emit(AccountDetailsAction.OnAccountDetailsLoadedFailed(it))
                            }
                        )
                    }.flowOn(Dispatchers.IO)
                )
            }
            is AccountDetailsAction.OnAccountDetailsLoadedFailed -> {
                state.copy(throwable = action.error, isLoading = false).withoutEffect()
            }
            is AccountDetailsAction.OnAccountDetailsLoadedSuccessful -> {
                state.copy(accountDetails = action.accountDetails, isLoading = false).withoutEffect()
            }
        }
    }
}