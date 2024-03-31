package com.toggl.komposable.sample.digibank.portfolio

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.toggl.komposable.sample.digibank.R
import com.toggl.komposable.sample.digibank.portfolio.data.PortfolioData

@Composable
fun Portfolio(state: PortfolioState) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(stringResource(R.string.portfolio), style = MaterialTheme.typography.displaySmall)
        Text(
            text = stringResource(R.string.to_improve_your_financial_health_save_money_invest_carefully_and_learn_about_finance),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            PortfolioList(state.portfolioDataList)
        }
    }
}

/**
 * This composable displays a list of portfolio items
 * @param portfolioData List of [PortfolioData]
 */
@Composable
fun PortfolioList(portfolioData: List<PortfolioData>) {
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
                    Text(stringResource(R.string.current_worth), style = MaterialTheme.typography.bodySmall)
                    Text(text = "$${it.value}", style = MaterialTheme.typography.titleMedium)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
