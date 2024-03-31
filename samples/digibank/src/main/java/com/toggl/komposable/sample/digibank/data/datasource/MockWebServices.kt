package com.toggl.komposable.sample.digibank.data.datasource

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.headersOf

val mockEngine = MockEngine { request ->
    val responseHeader = headersOf("Content-Type" to listOf("application/json"))
    when (request.url.encodedPath) {
        "/portfolio" -> {
            val response = """
                [
                    {
                        "type": "Stocks",
                        "value": 6789.0,
                        "currency": "USD"

                    },
                    {
                        "type": "Bonds",
                        "value": 234.0,
                        "currency": "USD"

                    },
                    {
                        "type": "Cash",
                        "value": 5464.0,
                        "currency": "USD"

                    },
                    {
                        "type": "Crypto",
                        "value": 4544.0,
                        "currency": "USD"
                    }
                ]
            """.trimIndent()
            respond(
                content = response,
                headers = responseHeader
            )
        }

        "/account" -> {
            val response = """
                {
                    "accountNumber": "123456789",
                    "accountType": "Savings",
                    "balance": 1559908.0,
                    "currency": "USD"
                }
            """.trimIndent()
            respond(
                content = response,
                headers = responseHeader
            )
        }

        "/transactions" -> {
            val response = """
                [
                    {
                        "date": "2021-01-01",
                        "description": "Salary",
                        "amount": 10000.0,
                        "currency": "USD"
                    },
                    {
                        "date": "2021-01-02",
                        "description": "Rent",
                        "amount": -2000.0,
                        "currency": "USD"
                    },
                    {
                        "date": "2021-01-03",
                        "description": "Groceries",
                        "amount": -100.0,
                        "currency": "USD"
                    },
                    {
                        "date": "2021-01-04",
                        "description": "Investment",
                        "amount": -5000.0,
                        "currency": "USD"
                    },
                    {
                        "date": "2021-01-05",
                        "description": "Bonus",
                        "amount": 5000.0,
                        "currency": "USD"
                    },
                    {
                        "date": "2021-01-06",
                        "description": "Dividends",
                        "amount": 100.0,
                        "currency": "USD"
                    },
                    {
                        "date": "2021-01-07",
                        "description": "Interest",
                        "amount": 50.0,
                        "currency": "USD"
                    }
                ]
            """.trimIndent()
            respond(
                content = response,
                headers = responseHeader
            )
        }

        else -> error("Unhandled ${request.url.encodedPath}")
    }

}