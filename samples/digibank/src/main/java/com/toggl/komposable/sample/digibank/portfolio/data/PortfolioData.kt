package com.toggl.komposable.sample.digibank.portfolio.data

import kotlinx.serialization.Serializable

@Serializable
data class PortfolioData(
    val type: String,
    val value: Double,
)