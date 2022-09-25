package com.kerite.pokedex

import android.app.Application
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class PokedexApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val isAnonymousAnalyticsEnabled = runBlocking {
            settingsDataStore.data.first()[SETTINGS_ANONYMOUS_ANALYTICS_ENABLED] ?: true
        }
        val isAutoCheckUpdateEnabled = runBlocking {
            settingsDataStore.data.first()[SETTINGS_AUTO_CHECK_UPDATE_KEY] ?: true
        }

        // Config AppCenter
        if (!BuildConfig.DEBUG && BuildConfig.APPCENTER_KEY.isNotBlank()) {
            if (isAnonymousAnalyticsEnabled) {
                AppCenter.start(
                    this,
                    Analytics::class.java,
                    Crashes::class.java
                )
            }
            if (isAutoCheckUpdateEnabled) {
                AppCenter.start(
                    this,
                    Distribute::class.java
                )
            }
        }


        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}