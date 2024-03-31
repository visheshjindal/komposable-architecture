package com.toggl.komposable.sample.digibank.settings

import com.toggl.komposable.architecture.ReduceResult
import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.extensions.withoutEffect
import com.toggl.komposable.sample.digibank.AppState

class SettingReducer: Reducer<AppState, SettingAction> {
    override fun reduce(
        state: AppState,
        action: SettingAction
    ): ReduceResult<AppState, SettingAction> {
        return when (action) {
            is SettingAction.ToggledShowBalance -> {
                state.copy(isLongPressBalanceEnabled = action.isEnabled).withoutEffect()
            }
            is SettingAction.ToggledShowPortfolioValue -> {
                state.copy(isLongPressPortfolioEnabled = action.isEnabled).withoutEffect()
            }
            is SettingAction.ToggledShowCurrency -> {
                state.copy(showCurrency = action.isEnabled).withoutEffect()
            }
        }
    }

}