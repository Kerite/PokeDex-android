package com.kerite.pokedex

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toFile
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.withContext
import timber.log.Timber

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

suspend fun <T> Context.putSetting(
    key: Preferences.Key<T>, newValue: T
) = withContext(Dispatchers.IO) {
    settingsDataStore.edit {
        it[key] = newValue
    }
}

suspend fun <T> Context.updateSetting(
    key: Preferences.Key<T>,
    operation: MutablePreferences.() -> Unit
) = withContext(Dispatchers.IO) {
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

fun Context.installApk(uri: Uri) {
    val file = uri.toFile()
    if (file.exists()) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/vnd.android.package-archive")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(intent)
    }
}