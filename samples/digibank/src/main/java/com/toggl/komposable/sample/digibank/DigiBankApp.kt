package com.toggl.komposable.sample.digibank

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Addchart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.toggl.komposable.sample.digibank.accounts.Accounts
import com.toggl.komposable.sample.digibank.portfolio.Portfolio
import com.toggl.komposable.sample.digibank.settings.SettingsPage

@Composable
fun DigiBankApp() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Accounts", "Portfolio", "Settings")
    Scaffold(
        topBar = {
            GreetingsAppBar()
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, label ->
                    NavigationBarItem(
                        icon = {
                            when (index) {
                                0 -> Icon(Icons.Filled.AccountBalance, contentDescription = label)
                                1 -> Icon(Icons.Filled.Addchart, contentDescription = label)
                                2 -> Icon(Icons.Filled.Settings, contentDescription = label)
                                else -> Icon(Icons.Filled.Home, contentDescription = label)
                            }
                        },
                        label = { Text(label) },
                        onClick = { selectedItem = index },
                        selected = selectedItem == index
                    )
                }
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colorScheme.background
        ) {
            AnimatedContent(selectedItem, label = "Dashboard Content") { selectedItem ->
                when (selectedItem) {
                    0 -> Accounts()
                    1 -> Portfolio()
                    2 -> SettingsPage()
                }
            }
        }
    }
}

@Composable
fun GreetingsAppBar() {
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "https://avatar.iran.liara.run/public",
            contentDescription = "User Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text("John Doe", style = MaterialTheme.typography.titleMedium)
            Text("Welcome back!", style = MaterialTheme.typography.bodySmall)
        }
    }
}