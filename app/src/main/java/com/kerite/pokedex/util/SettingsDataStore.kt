package com.kerite.pokedex.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.preference.PreferenceDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class SettingsDataStore(
    private val dataStore: DataStore<Preferences>
) : PreferenceDataStore() {
    override fun putBoolean(key: String, value: Boolean) {
        Timber.tag("DataStorePreference")
            .d("Put Boolean of $key, value is $value")
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit { it[booleanPreferencesKey(key)] = value }
        }
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return runBlocking {
            (dataStore.data.first()[booleanPreferencesKey(key)] ?: defValue).apply {
                Timber.tag("DataStorePreference")
                    .d("Get Boolean of $key, Default value is $defValue, Current value is $this")
            }
        }
    }

    override fun putString(key: String, value: String?) {
        Timber.tag("DataStorePreference")
            .d("Put String of $key, value is $value")
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit { it[stringPreferencesKey(key)] = value!! }
        }
    }

    override fun getString(key: String, defValue: String?): String? {
        return runBlocking {
            (dataStore.data.first()[stringPreferencesKey(key)] ?: defValue).apply {
                Timber.tag("DataStorePreference")
                    .d("Get String of $key, Default value is $defValue, Current value is $this")
            }
        }
    }

    override fun putInt(key: String, value: Int) {
        Timber.tag("DataStorePreference")
            .d("Put Int of $key, value is $value")
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit { it[intPreferencesKey(key)] = value }
        }
    }

    override fun getInt(key: String, defValue: Int): Int {
        return runBlocking {
            (dataStore.data.first()[intPreferencesKey(key)] ?: defValue).apply {
                Timber.tag("DataStorePreference")
                    .d("Get Int of $key, Default value is $defValue, Current value is $this")
            }
        }
    }
}