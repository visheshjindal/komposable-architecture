package com.toggl.komposable.sample.digibank

import com.toggl.komposable.architecture.NoEffect
import com.toggl.komposable.architecture.ReduceResult
import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.extensions.withoutEffect
import com.toggl.komposable.sample.digibank.settings.SettingAction

class GlobalReducer : Reducer<AppState, GlobalAction> {
    override fun reduce(
        state: AppState,
        action: GlobalAction
    ): ReduceResult<AppState, GlobalAction> {
        return when (action) {
            is GlobalAction.SettingActions -> {
                when (action.action) {
                    is SettingAction.ToggledShowBalance -> {
                        state.copy(isLongPressBalanceEnabled = action.action.isEnabled)
                            .withoutEffect()
                    }

                    is SettingAction.ToggledShowCurrency -> {
                        state.copy(showCurrency = action.action.isEnabled).withoutEffect()
                    }

                    is SettingAction.ToggledShowPortfolioValue -> {
                        state.copy(isLongPressPortfolioEnabled = action.action.isEnabled)
                            .withoutEffect()
                    }
                }
            }

            else -> ReduceResult(state, NoEffect)
        }
    }
}