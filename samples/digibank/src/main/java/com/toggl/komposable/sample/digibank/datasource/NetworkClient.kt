package com.toggl.komposable.sample.digibank.datasource

import com.toggl.komposable.sample.digibank.portfolio.data.PortfolioData
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
}