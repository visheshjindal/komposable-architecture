package com.toggl.komposable.sample.digibank.profile

import com.toggl.komposable.architecture.NoEffect
import com.toggl.komposable.architecture.ReduceResult
import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.extensions.withSuspendEffect
import com.toggl.komposable.extensions.withoutEffect

class ProfileReducer(private val loadProfileEffect: LoadProfileEffect) :
    Reducer<ProfileUIState, ProfileAction> {
    override fun reduce(
        state: ProfileUIState,
        action: ProfileAction
    ): ReduceResult<ProfileUIState, ProfileAction> {
        return when (action) {
            is ProfileAction.LoadProfile -> state.copy(isLoading = true).withSuspendEffect {
                loadProfileEffect.execute() ?: ProfileAction.OnProfileLoadedFailed(
                    Throwable("Profile loading failed")
                )
            }

            is ProfileAction.OnProfileLoadedSuccessful -> state.copy(
                isLoading = false,
                userDetails = action.profile
            ).withoutEffect()

            is ProfileAction.OnProfileLoadedFailed -> state.copy(
                isLoading = false,
                error = action.error
            ).withoutEffect()

            else -> ReduceResult(state, NoEffect)
        }
    }
}