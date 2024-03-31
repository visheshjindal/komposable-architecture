package com.toggl.komposable.sample.digibank.profile

import com.toggl.komposable.sample.digibank.data.UserDetails

sealed class ProfileAction {
    data object LoadProfile : ProfileAction()
    data class OnProfileLoadedSuccessful(val profile: UserDetails) : ProfileAction()
    data class OnProfileLoadedFailed(val error: Throwable) : ProfileAction()
}