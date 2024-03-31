package com.toggl.komposable.sample.digibank.settings

sealed class SettingAction {
    data class ToggledShowBalance(val isEnabled: Boolean) : SettingAction()
    data class ToggledShowPortfolioValue(val isEnabled: Boolean, val value: Boolean) : SettingAction()
    data class ToggledShowCurrency(val isEnabled: Boolean) : SettingAction()
}