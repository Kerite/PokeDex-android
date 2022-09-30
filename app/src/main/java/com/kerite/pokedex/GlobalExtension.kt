package com.kerite.pokedex

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.withContext

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

suspend fun <T> Context.putSetting(key: Preferences.Key<T>, newValue: T) =
    withContext(Dispatchers.IO) {
        settingsDataStore.edit {
            it[key] = newValue
        }
    }

suspend fun <T> Context.updateSetting(key: Preferences.Key<T>, operation: MutablePreferences.() -> Unit) =
    withContext(Dispatchers.IO) {
        settingsDataStore.edit(operation)
    }

suspend fun <T> Context.getSetting(key: Preferences.Key<T>, defaultValue: T) =
    withContext(Dispatchers.IO) {
        settingsDataStore.data.last()[key] ?: defaultValue
    }