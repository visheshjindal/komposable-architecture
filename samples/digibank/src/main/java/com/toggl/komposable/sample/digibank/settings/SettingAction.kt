package com.toggl.komposable.sample.digibank.settings

sealed class SettingAction {
    data class ToggledShowCurrency(val isEnabled: Boolean) : SettingAction()
}