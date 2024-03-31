package com.toggl.komposable.sample.digibank.portfolio

import com.toggl.komposable.architecture.ReduceResult
import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.extensions.withFlowEffect
import com.toggl.komposable.extensions.withoutEffect
import com.toggl.komposable.sample.digibank.data.datasource.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class PortfolioReducer : Reducer<PortfolioUIState, PortfolioAction> {
    override fun reduce(
        state: PortfolioUIState,
        action: PortfolioAction
    ): ReduceResult<PortfolioUIState, PortfolioAction> {
        return when (action) {
            is PortfolioAction.LoadPortfolio -> {
                state.copy(isLoading = true).withFlowEffect(
                    flow {
                        RepositoryImpl.fetchPortfolio().fold(
                            onSuccess = {
                                emit(PortfolioAction.OnPortfolioLoadedSuccessful(it))
                            },
                            onFailure = {
                                emit(PortfolioAction.OnPortfolioLoadedFailed(it))
                            }
                        )
                    }.flowOn(Dispatchers.IO)
                )
            }

            is PortfolioAction.OnPortfolioLoadedFailed -> {
                state.copy(throwable = action.error, isLoading = false).withoutEffect()
            }

            is PortfolioAction.OnPortfolioLoadedSuccessful -> {
                state.copy(portfolioDataList = action.portfolioList, isLoading = false)
                    .withoutEffect()
            }
        }
    }
}