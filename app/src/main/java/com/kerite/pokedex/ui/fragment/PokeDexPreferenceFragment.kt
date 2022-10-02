package com.kerite.pokedex.ui.fragment

import android.os.Bundle
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.TwoStatePreference
import com.kerite.fission.android.extensions.shortToast
import com.kerite.pokedex.R
import com.kerite.pokedex.SettingsConstants.SETTINGS_ANONYMOUS_ANALYTICS_ENABLED
import com.kerite.pokedex.SettingsConstants.SETTINGS_ANONYMOUS_ANALYTICS_ENABLED_STR
import com.kerite.pokedex.SettingsConstants.SETTINGS_AUTO_CHECK_UPDATE_KEY
import com.kerite.pokedex.SettingsConstants.SETTINGS_AUTO_CHECK_UPDATE_STR
import com.kerite.pokedex.SettingsConstants.SETTINGS_CHECK_UPDATE_STR
import com.kerite.pokedex.settingsDataStore
import com.kerite.pokedex.util.SettingsDataStore
import com.microsoft.appcenter.distribute.Distribute
import kotlinx.coroutines.launch
import timber.log.Timber

class PokeDexPreferenceFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore =
            SettingsDataStore(requireContext().settingsDataStore)
        setPreferencesFromResource(R.xml.preference_screen, rootKey)

//        (findPreference<SimpleMenuPreference>(SETTINGS_GAME_STR))?.apply {
//            setOnPreferenceChangeListener { preference, value ->
//                val gameVersion = value as String
//                Timber.tag("SettingsUpdate").i(value.toString())
//                true
//            }
//        }

        // 检查更新
        (findPreference<Preference>(SETTINGS_CHECK_UPDATE_STR))?.apply {
            setOnPreferenceClickListener {
                Timber.tag("UpdateChecker").i("Checking Update...")
                context.shortToast(resources.getString(R.string.toast_checking_update))
                Distribute.checkForUpdate()
                true
            }
        }
        // 匿名数据开关
        (findPreference<TwoStatePreference>(SETTINGS_ANONYMOUS_ANALYTICS_ENABLED_STR))?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                lifecycleScope.launch {
                    Timber.d("SETTINGS_ANONYMOUS_ANALYTICS_ENABLED $newValue")
                    context.settingsDataStore.edit { settings ->
                        settings[SETTINGS_ANONYMOUS_ANALYTICS_ENABLED] = newValue as Boolean
                    }
                }
                true
            }
        }
        // 自动检查更新开关
        (findPreference<TwoStatePreference>(SETTINGS_AUTO_CHECK_UPDATE_STR))?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                lifecycleScope.launch {
                    context.settingsDataStore.edit { settings ->
                        settings[SETTINGS_AUTO_CHECK_UPDATE_KEY] = newValue as Boolean
                    }
                }
                true
            }
        }
    }
}