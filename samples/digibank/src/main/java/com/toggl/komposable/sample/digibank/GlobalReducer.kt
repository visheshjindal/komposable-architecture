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

                    is SettingAction.ToggledShowCurrency -> {
                        state.copy(showCurrency = action.action.isEnabled).withoutEffect()
                    }


                }
            }

            else -> ReduceResult(state, NoEffect)
        }
    }
}