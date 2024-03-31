package com.toggl.komposable.sample.digibank.profile

import com.toggl.komposable.sample.digibank.data.UserDetails

data class ProfileUIState(
    val userDetails: UserDetails = UserDetails("", "", "", "", "", "", ""),
    val isLoading: Boolean = false,
    val error: Throwable? = null
)
