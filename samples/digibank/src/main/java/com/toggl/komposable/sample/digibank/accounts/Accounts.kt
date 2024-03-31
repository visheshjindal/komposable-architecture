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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.toggl.komposable.sample.digibank.GlobalAction
import com.toggl.komposable.sample.digibank.appStore
import com.toggl.komposable.sample.digibank.data.AccountDetails
import com.toggl.komposable.sample.digibank.data.TransactionDetails
import com.toggl.komposable.sample.digibank.extenstions.toCommaSeparatedString
import com.toggl.komposable.sample.digibank.portfolio.PortfolioAction
import com.toggl.komposable.sample.digibank.portfolio.PortfolioUIState
import com.toggl.komposable.sample.digibank.transactions.Transactions

@Composable
fun Accounts() {
    val accountStore = appStore.view<AccountDetailsUIState, AccountDetailsAction>(
        mapToLocalState = { it.accountDetailsUIState },
        mapToGlobalAction = { GlobalAction.AccountDetailsActions(it) }
    )

    val accountDetails by accountStore.state.collectAsState(initial = AccountDetailsUIState())

    Text(text = accountDetails.accountDetails.balance.toCommaSeparatedString())

//    Column(
//        modifier = Modifier
//            .fillMaxSize(),
//        horizontalAlignment = CenterHorizontally
//    ) {
//        Text(
//            text = "${accountDetails.accountDetails} ${accountDetails..toCommaSeparatedString()}",
//            style = MaterialTheme.typography.displaySmall,
//            maxLines = 1,
//            textAlign = TextAlign.Justify
//        )
//        Text(
//            text = "${accountDetails.accountType} - ${accountDetails.accountNumber}",
//            style = MaterialTheme.typography.labelMedium,
//            maxLines = 1,
//            textAlign = TextAlign.Justify
//        )
//        Spacer(modifier = Modifier.padding(8.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceAround
//        ) {
//            OutlinedButton(onClick = { /*TODO*/ }) {
//                Icon(
//                    Icons.Sharp.ArrowOutward,
//                    contentDescription = "Deposit",
//                    modifier = Modifier.rotate(180f)
//                )
//            }
//            OutlinedButton(
//                onClick = { /*TODO*/ }
//            ) {
//                Icon(
//                    Icons.Sharp.ArrowOutward,
//                    contentDescription = "Withdraw"
//                )
//            }
//        }
//        Spacer(modifier = Modifier.padding(8.dp))
//        Transactions(transactions = transactions)
//    }
}