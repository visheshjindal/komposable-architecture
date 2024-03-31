package com.toggl.komposable.sample.digibank.datasource

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.headersOf

val mockEngine = MockEngine {request ->
    val responseHeader = headersOf("Content-Type" to listOf("application/json"))
    when(request.url.encodedPath){
        "/portfolio" -> {
            val response = """
                [
                    {
                        "type": "Stocks",
                        "value": 6789.0
                    },
                    {
                        "type": "Bonds",
                        "value": 234.0
                    },
                    {
                        "type": "Cash",
                        "value": 5464.0
                    },
                    {
                        "type": "Crypto",
                        "value": 4544.0
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