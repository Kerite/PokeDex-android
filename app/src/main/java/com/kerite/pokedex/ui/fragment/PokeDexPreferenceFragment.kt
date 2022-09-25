package com.kerite.pokedex.ui.fragment

import android.os.Bundle
import androidx.datastore.preferences.core.edit
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.TwoStatePreference
import com.kerite.pokedex.R
import com.kerite.pokedex.SETTINGS_ANONYMOUS_ANALYTICS_ENABLED
import com.kerite.pokedex.SETTINGS_ANONYMOUS_ANALYTICS_ENABLED_STR
import com.kerite.pokedex.SETTINGS_CHECK_UPDATE_STR
import com.kerite.pokedex.settingsDataStore
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.analytics.EventProperties
import com.microsoft.appcenter.distribute.Distribute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class PokeDexPreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_screen, rootKey)

        (findPreference<Preference>(SETTINGS_CHECK_UPDATE_STR))?.apply {
            setOnPreferenceClickListener {
                Timber.tag("UpdateChecker").i("Checking Update...")
                Distribute.checkForUpdate()
                true
            }
        }

        (findPreference<TwoStatePreference>(SETTINGS_ANONYMOUS_ANALYTICS_ENABLED_STR))?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                CoroutineScope(Dispatchers.Default).launch {
                    context.settingsDataStore.edit { settings ->
                        settings[SETTINGS_ANONYMOUS_ANALYTICS_ENABLED] = newValue as Boolean
                    }
                    Analytics.trackEvent(
                        "Settings",
                        EventProperties().set("SETTINGS_ANONYMOUS_ANALYTICS_ENABLED", newValue as Boolean)
                    )
                }
                true
            }
        }
    }
}