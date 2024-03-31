package com.toggl.komposable.sample.digibank.data

import kotlinx.serialization.Serializable

@Serializable
data class PortfolioData(
    val currency: String,
    val type: String,
    val value: Double,
)