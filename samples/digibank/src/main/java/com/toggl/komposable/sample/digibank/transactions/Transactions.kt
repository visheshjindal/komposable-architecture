package com.toggl.komposable.sample.digibank.transactions

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowOutward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.toggl.komposable.sample.digibank.AppState
import com.toggl.komposable.sample.digibank.GlobalAction
import com.toggl.komposable.sample.digibank.R
import com.toggl.komposable.sample.digibank.appStore
import com.toggl.komposable.sample.digibank.data.TransactionDetails
import com.toggl.komposable.sample.digibank.extenstions.toCommaSeparatedString
import com.toggl.komposable.sample.digibank.extenstions.toFormattedCurrencyString
import com.toggl.komposable.sample.digibank.extenstions.toFormattedDateString

@Composable
fun Transactions() {

    val transactionViewStore = appStore.view<TransactionsUIState, TransactionsAction>(
        mapToLocalState = { it.transactionsUIState },
        mapToGlobalAction = { GlobalAction.TransactionsActions(it) }
    )

    LaunchedEffect(Unit) {
        transactionViewStore.send(TransactionsAction.LoadTransactions)
    }

    val transactions by transactionViewStore.state.collectAsStateWithLifecycle(
        initialValue = TransactionsUIState())

    val appState by appStore.state.collectAsStateWithLifecycle(initialValue = AppState())

    AnimatedContent(targetState = transactions.isLoading, label = "Transaction View") {isLoading ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            TransactionListView(transactions.transactions, showCurrency = appState.showCurrency)
        }
    }
}

@Composable
fun TransactionListView(transactions: List<TransactionDetails>, showCurrency: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Text(
            text = stringResource(R.string.transactions),
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 1,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        LazyColumn {
            items(transactions, key = { it.date }) { transactions ->
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    border = BorderStroke(0.dp, Color.White)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(transactions.description, style = MaterialTheme.typography.bodyMedium)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(transactions.amount.toFormattedCurrencyString(transactions.currency, showCurrency), style = MaterialTheme.typography.labelLarge)
                            Text(transactions.date.toFormattedDateString(), style = MaterialTheme.typography.bodySmall)
                        }
                        Icon(
                            Icons.Sharp.ArrowOutward,
                            contentDescription = "Transaction Icon",
                            modifier = Modifier
                                .padding(16.dp)
                                .rotate(if (transactions.amount < 0) 0f else 180f),
                            tint = if (transactions.amount > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}
