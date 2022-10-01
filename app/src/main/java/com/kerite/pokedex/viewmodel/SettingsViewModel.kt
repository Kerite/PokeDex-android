package com.kerite.pokedex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kerite.pokedex.SettingsConstants.SETTINGS_ANONYMOUS_ANALYTICS_ENABLED
import com.kerite.pokedex.SettingsConstants.SETTINGS_ANONYMOUS_ANALYTICS_ENABLED_DEFAULT
import com.kerite.pokedex.settingsDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SettingsViewModel(
    application: Application
) : AndroidViewModel(application) {
    val dataStore = application.settingsDataStore
    val anonymousAnalyticsEnabled: StateFlow<Boolean> = application.settingsDataStore
        .data.map { preferences ->
            preferences[SETTINGS_ANONYMOUS_ANALYTICS_ENABLED]
                ?: SETTINGS_ANONYMOUS_ANALYTICS_ENABLED_DEFAULT
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            SETTINGS_ANONYMOUS_ANALYTICS_ENABLED_DEFAULT
        )
}