package com.toggl.komposable.sample.digibank.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsPage() {
    // Create a Column with a list of items
    Column {
        // Create a list of items with the label and switch
        Text(
            "Settings",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(16.dp)
        )
        listOf(
            "Show Balance" to true,
            "Show Portfolio Value" to true,
            "Show Transactions" to true,
        ).forEach { (label, isChecked) ->
            // Create a Row with the label and switch
            Card(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = isChecked,
                        onCheckedChange = { /*TODO*/ }
                    )
                }
            }

        }
    }
}