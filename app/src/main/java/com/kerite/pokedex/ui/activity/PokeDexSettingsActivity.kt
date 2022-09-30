package com.kerite.pokedex.ui.activity

import android.os.Bundle
import com.kerite.pokedex.databinding.ActivitySettingsBinding
import com.kerite.pokedex.ui.PokeDexBaseActivity
import com.kerite.pokedex.ui.fragment.PokeDexPreferenceFragment

class PokeDexSettingsActivity : PokeDexBaseActivity<ActivitySettingsBinding>(
    ActivitySettingsBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setSupportActionBar(toolbar)
            supportFragmentManager
                .beginTransaction()
                .replace(settingsFragmentContainer.id, PokeDexPreferenceFragment())
                .commit()
        }
    }
}