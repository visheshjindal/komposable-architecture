package com.toggl.komposable.sample.digibank.datasource

// Create a repository that fetches the portfolio data with good testability
object PortfolioRepository {
    var networkClient = NetworkClient()
    suspend fun fetchPortfolio() = networkClient.fetchPortfolio()
}