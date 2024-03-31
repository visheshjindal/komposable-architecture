package com.toggl.komposable.sample.digibank.accounts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowOutward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.toggl.komposable.sample.digibank.GlobalAction
import com.toggl.komposable.sample.digibank.appStore
import com.toggl.komposable.sample.digibank.extenstions.toCommaSeparatedString
import com.toggl.komposable.sample.digibank.transactions.Transactions

@Composable
fun Accounts() {
    val accountStore = appStore.view<AccountDetailsUIState, AccountDetailsAction>(
        mapToLocalState = { it.accountDetailsUIState },
        mapToGlobalAction = { GlobalAction.AccountDetailsActions(it) }
    )

    LaunchedEffect(Unit) {
        accountStore.send(AccountDetailsAction.LoadAccountDetails)
    }

    val accountDetails by accountStore.state.collectAsStateWithLifecycle(initialValue = AccountDetailsUIState())

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            text = "${accountDetails.accountDetails.currency} ${accountDetails.accountDetails.balance.toCommaSeparatedString()}",
            style = MaterialTheme.typography.displaySmall,
            maxLines = 1,
            textAlign = TextAlign.Justify
        )
        Text(
            text = "${accountDetails.accountDetails.accountType} - ${accountDetails.accountDetails.accountNumber}",
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            textAlign = TextAlign.Justify
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Sharp.ArrowOutward,
                    contentDescription = "Deposit",
                    modifier = Modifier.rotate(180f)
                )
            }
            OutlinedButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    Icons.Sharp.ArrowOutward,
                    contentDescription = "Withdraw"
                )
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Transactions()
    }
}