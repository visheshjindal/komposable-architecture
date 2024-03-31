package com.toggl.komposable.sample.digibank.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Addchart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.toggl.komposable.sample.digibank.accounts.Accounts
import com.toggl.komposable.sample.digibank.portfolio.Portfolio
import com.toggl.komposable.sample.digibank.profile.Profile
import com.toggl.komposable.sample.digibank.settings.SettingsPage


sealed class BottomNavigationItems(val route: String, val icon: ImageVector, val label: String) {
    data object Accounts :
        BottomNavigationItems("/accounts", Icons.Filled.AccountBalance, "Accounts")

    data object Portfolio : BottomNavigationItems("/portfolio", Icons.Filled.Addchart, "Portfolio")
    data object Settings : BottomNavigationItems("/settings", Icons.Filled.Settings, "Settings")
}

sealed class ScreenRoutes(val route: String) {
    data object BottomBar : ScreenRoutes("/bottombar")
    data object Profile : ScreenRoutes("/profile")

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
    NavHost(navController, startDestination = ScreenRoutes.BottomBar.route) {
        navigation(
            route = ScreenRoutes.BottomBar.route,
            startDestination = BottomNavigationItems.Accounts.route
        ) {
            composable(BottomNavigationItems.Accounts.route) { Accounts() }
            composable(BottomNavigationItems.Portfolio.route) { Portfolio() }
            composable(BottomNavigationItems.Settings.route) { SettingsPage() }
        }
        composable(ScreenRoutes.Profile.route) { Profile(navController) }
    }
}