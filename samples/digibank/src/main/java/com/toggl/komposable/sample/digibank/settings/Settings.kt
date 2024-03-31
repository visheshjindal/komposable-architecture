package com.toggl.komposable.sample.digibank.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.toggl.komposable.sample.digibank.AppState
import com.toggl.komposable.sample.digibank.R
import com.toggl.komposable.sample.digibank.appStore
import com.toggl.komposable.sample.digibank.mapSettingActionToGlobalAction

@Composable
fun SettingsPage() {
    val state by appStore.state.collectAsStateWithLifecycle(initialValue = AppState())

    Column {
        Text(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(16.dp)
        )
        SettingsCard(
            label = stringResource(R.string.long_press_to_show_balance),
            isChecked = state.isLongPressBalanceEnabled,
            onCheckedChange = { newValue ->
                appStore.send(mapSettingActionToGlobalAction(SettingAction.ToggledShowBalance(newValue)))
            }
        )
        SettingsCard(
            label = stringResource(R.string.long_press_to_portfolio_value),
            isChecked = state.isLongPressPortfolioEnabled,
            onCheckedChange = { newValue ->
                appStore.send(mapSettingActionToGlobalAction(SettingAction.ToggledShowPortfolioValue(newValue)))
            }
        )
        SettingsCard(
            label = stringResource(R.string.show_currency),
            isChecked = state.showCurrency,
            onCheckedChange = { newValue ->
                appStore.send(mapSettingActionToGlobalAction(SettingAction.ToggledShowCurrency(newValue)))
            }
        )
    }
}

@Composable
fun SettingsCard(label: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
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
            Switch(checked = isChecked, onCheckedChange = onCheckedChange)
        }
    }
}