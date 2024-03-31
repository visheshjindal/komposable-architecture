package com.toggl.komposable.sample.digibank.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.toggl.komposable.sample.digibank.GlobalAction
import com.toggl.komposable.sample.digibank.appStore
import com.toggl.komposable.sample.digibank.mapProfileActionToGlobalAction

@Composable
fun Profile(navHostController: NavHostController) {

    val profileStore = appStore.view<ProfileUIState, ProfileAction>(
        mapToLocalState = { it.profileUIState },
        mapToGlobalAction = { GlobalAction.ProfileActions(it) }
    )

    val profileUIState by profileStore.state.collectAsStateWithLifecycle(initialValue = ProfileUIState())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        AsyncImage(
            model = profileUIState.userDetails.profilePicture,
            contentDescription = "User Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(124.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "${profileUIState.userDetails.first} ${profileUIState.userDetails.last}",
            style = MaterialTheme.typography.displaySmall
        )
        Text(text = profileUIState.userDetails.email, style = MaterialTheme.typography.headlineSmall)
        Text(text = profileUIState.userDetails.phone, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = profileUIState.userDetails.address, style = MaterialTheme.typography.labelMedium)
    }

    BackHandler {
        appStore.send(mapProfileActionToGlobalAction(ProfileAction.BackPressed))
        navHostController.popBackStack()
    }
}
