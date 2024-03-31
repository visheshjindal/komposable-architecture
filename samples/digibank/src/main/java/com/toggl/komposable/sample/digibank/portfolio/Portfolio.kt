package com.toggl.komposable.sample.digibank.portfolio

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.toggl.komposable.sample.digibank.GlobalAction
import com.toggl.komposable.sample.digibank.R
import com.toggl.komposable.sample.digibank.appStore
import com.toggl.komposable.sample.digibank.data.PortfolioData
import com.toggl.komposable.sample.digibank.extenstions.toCommaSeparatedString

@Composable
fun Portfolio() {

    val portfolioStore = appStore.view<PortfolioUIState, PortfolioAction>(
        mapToLocalState = { it.portfolioUIState },
        mapToGlobalAction = { GlobalAction.PortfolioActions(it) }
    )

    val portfolioState by portfolioStore.state.collectAsStateWithLifecycle(
        initialValue = PortfolioUIState(),
        minActiveState = androidx.lifecycle.Lifecycle.State.RESUMED
    )

    LaunchedEffect(Unit) {
        portfolioStore.send(PortfolioAction.LoadPortfolio)
    }

    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(stringResource(R.string.portfolio), style = MaterialTheme.typography.displaySmall)
        Text(
            text = stringResource(R.string.to_improve_your_financial_health_save_money_invest_carefully_and_learn_about_finance),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        AnimatedContent(
            targetState = portfolioState.isLoading,
            label = "Portfolio Content"
        ) { isLoading ->
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            } else {
                PortfolioList(portfolioState.portfolioDataList)
            }
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
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(it.type, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        stringResource(R.string.current_worth),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "${it.currency} ${it.value.toCommaSeparatedString()}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
