package com.toggl.komposable.sample.digibank

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.toggl.komposable.sample.digibank.data.UserDetails
import com.toggl.komposable.sample.digibank.navigation.BottomNavigationBar
import com.toggl.komposable.sample.digibank.navigation.NavigationHost
import com.toggl.komposable.sample.digibank.navigation.ScreenRoutes
import com.toggl.komposable.sample.digibank.profile.ProfileAction

@Composable
fun DigiBankApp() {
    val navController = rememberNavController()
    val appState by appStore.state.collectAsState(initial = AppState())

    LaunchedEffect(Unit) {
        appStore.send(GlobalAction.ProfileActions(ProfileAction.LoadProfile))
    }

    Scaffold(
        topBar = {
            AnimatedContent(targetState = appState.currentRoutes, label = "Title Bar") { routes ->

                when (routes) {
                    is ScreenRoutes.BottomBar -> {
                        AnimatedContent(
                            targetState = appState.profileUIState.isLoading,
                            label = "Top bar"
                        ) { isLoading ->
                            if (isLoading) {
                                Text(
                                    text = "Welcome Back!",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(16.dp)
                                )
                            } else {
                                HomeAppBar(appState.profileUIState.userDetails) {
                                    appStore.send(GlobalAction.OnTapNavigationToProfile)
                                    navController.navigate(ScreenRoutes.Profile.route)

                                }
                            }
                        }
                    }

                    is ScreenRoutes.Profile -> {
                        BackAppBar {
                            appStore.send(GlobalAction.ProfileActions(ProfileAction.BackPressed))
                            navController.popBackStack()
                        }
                    }
                }

            }
        },
        bottomBar = {
            AnimatedVisibility(visible = appState.currentRoutes == ScreenRoutes.BottomBar) {
                BottomNavigationBar(navController)
            }
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

@Composable
fun HomeAppBar(userDetails: UserDetails, onProfilePictureClick: () -> Unit = {}) {
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = userDetails.profilePicture,
            contentDescription = "User Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .clickable { onProfilePictureClick() }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                "${userDetails.first} ${userDetails.last}",
                style = MaterialTheme.typography.titleMedium
            )
            Text("Welcome back!", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun BackAppBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowCircleLeft,
            tint = MaterialTheme.colorScheme.surfaceTint,
            contentDescription = "Back",
            modifier = Modifier
                .size(48.dp)
                .clickable { onBackClick() }
        )
    }
}