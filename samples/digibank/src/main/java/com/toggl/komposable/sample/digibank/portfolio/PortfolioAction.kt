package com.toggl.komposable.sample.digibank.portfolio

import com.toggl.komposable.architecture.ReduceResult
import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.extensions.withFlowEffect
import com.toggl.komposable.extensions.withoutEffect
import com.toggl.komposable.sample.digibank.data.datasource.Repository
import com.toggl.komposable.sample.digibank.data.PortfolioData
import com.toggl.komposable.sample.digibank.data.datasource.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

sealed class PortfolioAction {
    data object LoadPortfolio : PortfolioAction()
    data class OnPortfolioLoadedSuccessful(val portfolioList: List<PortfolioData>) :
        PortfolioAction()

    data class OnPortfolioLoadedFailed(val error: Throwable) : PortfolioAction()
}