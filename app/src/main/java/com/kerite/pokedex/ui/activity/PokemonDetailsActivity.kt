package com.kerite.pokedex.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.kerite.pokedex.databinding.ActivityPokemonDetailsBinding
import com.kerite.pokedex.entity.PokemonDetailsEntity
import com.kerite.pokedex.model.PokemonDetails
import com.kerite.pokedex.ui.BaseActivity
import com.kerite.pokedex.viewmodel.DetailsActivityViewModel
import kotlinx.coroutines.launch

class PokemonDetailsActivity : BaseActivity<ActivityPokemonDetailsBinding>() {
    private lateinit var viewModel: DetailsActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailsActivityViewModel::class.java]
        viewModel.changePokemonDexNumber(intent.getIntExtra(INTENT_DEX_NUMBER, 0))

        binding.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            lifecycleScope.launch {
                viewModel.currentPokemonDetail.collect {
                    when (it) {
                        is PokemonDetails.Found -> {
                            updateDetails(it.details)
                        }
                        else -> {
                            initPokemonDetail()
                        }
                    }
                }
            }
        }
    }

    private fun initPokemonDetail() {
        binding.apply {
            collapsingToolbar.title = "Pokemon Dex"
            evHp.text = "0"
            evAttack.text = "0"
            evDefence.text = "0"
            evSpecialAttack.text = "0"
            evSpecialDefence.text = "0"
            evSpeed.text = "0"

            pokemonStrength.hp = 0
            pokemonStrength.attack = 0
            pokemonStrength.defence = 0
            pokemonStrength.specialAttack = 0
            pokemonStrength.specialDefence = 0
            pokemonStrength.speed = 0
        }
    }

    private fun updateDetails(details: PokemonDetailsEntity) {
        Log.d("PokemonChanged", details.toString())
        binding.apply {
            collapsingToolbar.title = details.name
            pokemonJpName.text = details.jpName
            pokemonEnName.text = details.enName

            //<editor-fold desc="读取努力值">
            evHp.text = details.evHp.toString()
            evAttack.text = details.evAttack.toString()
            evDefence.text = details.evDefence.toString()
            evSpecialAttack.text = details.evSpecialAttack.toString()
            evSpecialDefence.text = details.evSpecialDefence.toString()
            evSpeed.text = details.evSpeed.toString()
            //</editor-fold>

            //<editor-fold desc="读取种族值">
            pokemonStrength.hp = details.hp
            pokemonStrength.attack = details.attack
            pokemonStrength.defence = details.defence
            pokemonStrength.specialAttack = details.specialAttack
            pokemonStrength.specialDefence = details.specialDefence
            pokemonStrength.speed = details.speed
            //</editor-fold>
        }
    }

    companion object {
        const val INTENT_DEX_NUMBER = "pokemon_dex"
    }
}