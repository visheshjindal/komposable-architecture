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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Accounts() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            text = "$1,55,807.19",
            style = MaterialTheme.typography.displayMedium,
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