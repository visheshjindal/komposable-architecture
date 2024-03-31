package com.toggl.komposable.sample.digibank.data.datasource

import com.toggl.komposable.sample.digibank.data.AccountDetails
import com.toggl.komposable.sample.digibank.data.PortfolioData
import com.toggl.komposable.sample.digibank.data.TransactionDetails

// Create a repository that fetches the portfolio data with good testability
object RepositoryImpl : Repository {
    private val networkClient = NetworkClient()
    override suspend fun fetchPortfolio() = networkClient.fetchPortfolio()
    override suspend fun fetchAccount() = networkClient.fetchAccount()
    override suspend fun fetchTransactions() = networkClient.fetchTransactions()
}

interface Repository {
    suspend fun fetchPortfolio(): Result<List<PortfolioData>>
    suspend fun fetchAccount(): Result<AccountDetails>
    suspend fun fetchTransactions(): Result<List<TransactionDetails>>
}