package com.kerite.pokedex

import android.app.Application
import com.kerite.pokedex.intergrates.PokeDexDistributeListener
import com.kerite.pokedex.intergrates.TimberReleaseTree
import com.kerite.pokedex.intergrates.TimberThreadAwareTree
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
        if (BuildConfig.DEBUG) {
            Timber.plant(TimberThreadAwareTree())
        } else {
            Timber.plant(TimberReleaseTree())
        }
        val isAnonymousAnalyticsEnabled = runBlocking {
            settingsDataStore.data.first()[SETTINGS_ANONYMOUS_ANALYTICS_ENABLED] ?: SETTINGS_ANONYMOUS_ANALYTICS_ENABLED_DEFAULT
        }
        val isAutoCheckUpdateEnabled = runBlocking {
            settingsDataStore.data.first()[SETTINGS_AUTO_CHECK_UPDATE_KEY] ?: SETTINGS_AUTO_CHECK_UPDATE_DEFAULT
        }
        Timber.tag("AppCenterStat").d("AnonymousAnalytics $isAnonymousAnalyticsEnabled AutoCheckUpdate $isAutoCheckUpdateEnabled")

        // Config AppCenter
        if (!BuildConfig.DEBUG && BuildConfig.APPCENTER_KEY.isNotBlank()) {
            if (!isAnonymousAnalyticsEnabled) {
                Timber.tag("AppCenterStat").i("Anonymous Analytics Disable")
                Analytics.setEnabled(false)
                Crashes.setEnabled(false)
            }
            if (!isAutoCheckUpdateEnabled) {
                Distribute.disableAutomaticCheckForUpdate()
            }
            Distribute.setListener(PokeDexDistributeListener())
            AppCenter.start(
                this,
                BuildConfig.APPCENTER_KEY,
                Analytics::class.java,
                Crashes::class.java,
                Distribute::class.java
            )
            Analytics.isEnabled().thenAccept {
                if (it) Timber.tag("AppCenterStat").i("Analytics Enabled")
            }
            Crashes.isEnabled().thenAccept {
                if (it) Timber.tag("AppCenterStat").i("Crashes Enabled")
            }
            Distribute.isEnabled().thenAccept {
                if (it) Timber.tag("AppCenterStat").i("Distribute Enabled")
            }
        } else if (!BuildConfig.DEBUG && BuildConfig.APPCENTER_KEY.isBlank()) {
            Timber.tag("AppCenterStat").e("AppCenter secret is empty")
        }
    }
}