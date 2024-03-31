package com.toggl.komposable.sample.digibank

import com.toggl.komposable.architecture.NoEffect
import com.toggl.komposable.architecture.ReduceResult
import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.extensions.withoutEffect
import com.toggl.komposable.sample.digibank.navigation.ScreenRoutes
import com.toggl.komposable.sample.digibank.profile.ProfileAction
import com.toggl.komposable.sample.digibank.settings.SettingAction

class GlobalReducer : Reducer<AppState, GlobalAction> {
    override fun reduce(
        state: AppState,
        action: GlobalAction
    ): ReduceResult<AppState, GlobalAction> {
        return when (action) {
            is GlobalAction.SettingActions -> {
                when (action.action) {

                    is SettingAction.ToggledShowCurrency -> {
                        state.copy(showCurrency = action.action.isEnabled).withoutEffect()
                    }
                }
            }

            is GlobalAction.OnTapNavigationToProfile -> {
                state.copy(currentRoutes = ScreenRoutes.Profile).withoutEffect()
            }

            is GlobalAction.ProfileActions -> {
                if (action.action is ProfileAction.BackPressed) {
                    state.copy(currentRoutes = ScreenRoutes.BottomBar).withoutEffect()
                } else {
                    ReduceResult(state, NoEffect)
                }
            }

            else -> ReduceResult(state, NoEffect)
        }
    }
}