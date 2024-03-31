package com.toggl.komposable.sample.digibank.portfolio

import com.toggl.komposable.sample.digibank.data.PortfolioData

data class PortfolioUIState(
    val portfolioDataList: List<PortfolioData> = emptyList(),
    val throwable: Throwable? = null,
    val isLoading: Boolean = false,
)