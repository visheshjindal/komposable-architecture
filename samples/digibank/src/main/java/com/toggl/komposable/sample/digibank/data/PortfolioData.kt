package com.toggl.komposable.sample.digibank.data

import kotlinx.serialization.Serializable

@Serializable
data class PortfolioData(
    val currency: String = "USD",
    val type: String,
    val value: Double,
)