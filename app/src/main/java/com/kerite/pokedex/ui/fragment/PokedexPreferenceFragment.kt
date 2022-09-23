package com.kerite.pokedex.ui.fragment

import android.os.Bundle
import androidx.datastore.preferences.core.edit
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.TwoStatePreference
import com.kerite.pokedex.R
import com.kerite.pokedex.SETTINGS_ANONYMOUS_ANALYTICS_ENABLED
import com.kerite.pokedex.SETTINGS_ANONYMOUS_ANALYTICS_ENABLED_STR
import com.kerite.pokedex.settingsDataStore
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.analytics.EventProperties
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokedexPreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_screen, rootKey)

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