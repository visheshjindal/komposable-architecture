package com.toggl.komposable.sample.digibank

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.toggl.komposable.sample.digibank.accounts.Accounts
import com.toggl.komposable.sample.digibank.portfolio.Portfolio
import com.toggl.komposable.sample.digibank.settings.SettingsPage

@Composable
fun DigiBankApp() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            GreetingsAppBar()
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colorScheme.background
        ) {
            NavigationHost(navController)
        }
    }
}

sealed class BottomNavigationItems(val route: String, val icon: ImageVector, val label: String) {
    data object Accounts : BottomNavigationItems("accounts", Icons.Filled.AccountBalance, "Accounts")
    data object Portfolio : BottomNavigationItems("portfolio", Icons.Filled.Addchart, "Portfolio")
    data object Settings : BottomNavigationItems("settings", Icons.Filled.Settings, "Settings")
}

val bottomNavigationItems = listOf(
    BottomNavigationItems.Accounts,
    BottomNavigationItems.Portfolio,
    BottomNavigationItems.Settings
)

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        bottomNavigationItems.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavigationItems.Accounts.route) {
        composable(BottomNavigationItems.Accounts.route) { Accounts() }
        composable(BottomNavigationItems.Portfolio.route) { Portfolio() }
        composable(BottomNavigationItems.Settings.route) { SettingsPage() }
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