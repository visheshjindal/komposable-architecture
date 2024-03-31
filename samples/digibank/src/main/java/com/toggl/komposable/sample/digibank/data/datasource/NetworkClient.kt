package com.toggl.komposable.sample.digibank.data.datasource

import com.toggl.komposable.sample.digibank.data.AccountDetails
import com.toggl.komposable.sample.digibank.data.PortfolioData
import com.toggl.komposable.sample.digibank.data.TransactionDetails
import com.toggl.komposable.sample.digibank.data.UserDetails
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json

class NetworkClient(
    private val baseUrl: String = "http://localhost:8080",
    engine: HttpClientEngine = mockEngine
) {
    private val client = HttpClient(engine) {
        install(ContentNegotiation) {
            json(Json{
                prettyPrint = true
                isLenient = true
            })
        }
    }
    suspend fun fetchPortfolio(): Result<List<PortfolioData>> = runCatching {
        delay(1000)
        client.get("$baseUrl/portfolio").body()
    }

    suspend fun fetchAccount(): Result<AccountDetails> = runCatching {
        delay(1000)
        client.get("$baseUrl/account").body()
    }

    suspend fun fetchTransactions(): Result<List<TransactionDetails>> = runCatching {
        delay(3000)
        client.get("$baseUrl/transactions").body()
    }

    suspend fun fetchUserDetails(): Result<UserDetails> = runCatching {
        delay(1000)
        client.get("$baseUrl/userDetails").body()
    }
}