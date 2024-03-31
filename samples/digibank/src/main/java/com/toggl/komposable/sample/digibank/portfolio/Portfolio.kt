package com.toggl.komposable.sample.digibank.portfolio

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.toggl.komposable.sample.digibank.datasource.NetworkClient
import com.toggl.komposable.sample.digibank.datasource.PortfolioRepository
import com.toggl.komposable.sample.digibank.portfolio.data.PortfolioData

@Composable
fun Portfolio() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        var portfolioData by remember { mutableStateOf(emptyList<PortfolioData>()) }

        LaunchedEffect(key1 = currentCompositionLocalContext) {
            try {
                val data = PortfolioRepository.fetchPortfolio().fold(
                    onSuccess = { it },
                    onFailure = {
                        Log.e("Portfolio", "Error fetching portfolio data", it)
                        emptyList()
                    }
                )
                portfolioData = data
            } catch (e: Exception) {
                Log.e("Portfolio", "Error fetching portfolio data", e)
            }
        }
        Text("Portfolio", style = MaterialTheme.typography.displaySmall)
        Text(text = "To improve your financial health, save money,\n invest carefully, and learn about finance.", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        // Create a lazy column to display the portfolio data
        LazyColumn {
            items(portfolioData, key = { it.type }) {
                Card(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(),
                ){
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(it.type, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.weight(1f))
                        Text("Current Worth", style = MaterialTheme.typography.bodySmall)
                        Text(text = "$${it.value}", style = MaterialTheme.typography.titleMedium)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}