package com.toggl.komposable.sample.digibank.portfolio

import com.toggl.komposable.sample.digibank.data.datasource.Repository
import com.toggl.komposable.sample.digibank.data.datasource.RepositoryImpl
import com.toggl.komposable.utils.SuspendEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadPortfolioEffect(private val repository: Repository = RepositoryImpl): SuspendEffect<PortfolioAction>() {
    override suspend fun execute(): PortfolioAction? = withContext(Dispatchers.IO) {
        repository.fetchPortfolio().fold(
            onSuccess = { portfolioList ->
                PortfolioAction.OnPortfolioLoadedSuccessful(portfolioList)
            },
            onFailure = { error ->
                PortfolioAction.OnPortfolioLoadedFailed(error)
            }
        )
    }
}