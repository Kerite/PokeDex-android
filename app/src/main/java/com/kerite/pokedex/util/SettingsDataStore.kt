package com.kerite.pokedex.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.preference.PreferenceDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SettingsDataStore(
    private val dataStore: DataStore<Preferences>
) : PreferenceDataStore() {
    override fun putBoolean(key: String, value: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit { it[booleanPreferencesKey(key)] = value }
        }
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return runBlocking { dataStore.data.first()[booleanPreferencesKey(key)] ?: defValue }
    }

    override fun putString(key: String, value: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit { it[stringPreferencesKey(key)] = value!! }
        }
    }

    override fun getString(key: String, defValue: String?): String? {
        return runBlocking { dataStore.data.first()[stringPreferencesKey(key)] ?: defValue }
    }
}