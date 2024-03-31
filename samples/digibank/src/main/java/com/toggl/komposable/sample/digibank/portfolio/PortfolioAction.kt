package com.toggl.komposable.sample.digibank.portfolio

import android.util.Log
import com.toggl.komposable.architecture.ReduceResult
import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.extensions.withEffect
import com.toggl.komposable.extensions.withFlowEffect
import com.toggl.komposable.extensions.withSuspendEffect
import com.toggl.komposable.extensions.withoutEffect
import com.toggl.komposable.sample.digibank.datasource.PortfolioRepository
import com.toggl.komposable.sample.digibank.portfolio.data.PortfolioData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

sealed class PortfolioAction {
    data object LoadPortfolio : PortfolioAction()
    data class OnPortfolioLoadedSuccessful(val portfolioList: List<PortfolioData>) :
        PortfolioAction()

    data class OnPortfolioLoadedFailed(val error: Throwable) : PortfolioAction()
}

data class PortfolioState(
    val portfolioDataList: List<PortfolioData> = emptyList(),
    val throwable: Throwable? = null,
    val isLoading: Boolean = false,
)

class PortfolioReducer : Reducer<PortfolioState, PortfolioAction> {
    override fun reduce(
        state: PortfolioState,
        action: PortfolioAction
    ): ReduceResult<PortfolioState, PortfolioAction> {
        return when (action) {
            is PortfolioAction.LoadPortfolio -> {
                state.copy(isLoading = true).withFlowEffect(
                    flow {
                        PortfolioRepository.fetchPortfolio().fold(
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