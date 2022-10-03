package com.kerite.pokedex

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.kerite.pokedex.util.SimpleFragmentViewPagerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.withContext
import timber.log.Timber

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

suspend fun <T> Context.putSetting(key: Preferences.Key<T>, newValue: T) =
    withContext(Dispatchers.IO) {
        settingsDataStore.edit {
            it[key] = newValue
        }
    }

suspend fun <T> Context.updateSetting(
    key: Preferences.Key<T>,
    operation: MutablePreferences.() -> Unit
) =
    withContext(Dispatchers.IO) {
        settingsDataStore.edit(operation)
    }

suspend fun <T> Context.getSetting(key: Preferences.Key<T>, defaultValue: T) =
    withContext(Dispatchers.IO) {
        settingsDataStore.data.last()[key] ?: defaultValue
    }

inline fun <T> measurePerformance(
    tag: String,
    action: () -> T
): T {
    val startTime = System.currentTimeMillis()
    return action().also {
        Timber.tag("Performance[$tag]")
            .d("Used ${System.currentTimeMillis() - startTime} milliseconds")
    }
}

fun FragmentActivity.createAdapter(
    vararg resIdToFragmentPair: Pair<Int, () -> Fragment>
): SimpleFragmentViewPagerAdapter {
    return SimpleFragmentViewPagerAdapter(
        this, hashMapOf(*resIdToFragmentPair)
    )
}