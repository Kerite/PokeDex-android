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

        // Config AppCenter
        if (!BuildConfig.DEBUG && isAnonymousAnalyticsEnabled && BuildConfig.APPCENTER_KEY.isNotBlank()) {
            AppCenter.start(
                this,
                BuildConfig.APPCENTER_KEY,
                Distribute::class.java,
                Crashes::class.java,
                Analytics::class.java
            )
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}