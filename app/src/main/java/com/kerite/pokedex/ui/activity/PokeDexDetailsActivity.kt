package com.kerite.pokedex.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.kerite.fission.AntiShaker
import com.kerite.fission.android.ui.SimpleFragmentViewPagerAdapter
import com.kerite.pokedex.R
import com.kerite.pokedex.database.entity.PokemonDetailsEntity
import com.kerite.pokedex.databinding.ActivityPokemonDetailsBinding
import com.kerite.pokedex.model.PokemonDetails
import com.kerite.pokedex.ui.PokeDexBaseActivity
import com.kerite.pokedex.ui.dialog.SwitchFormDialogFragment
import com.kerite.pokedex.ui.fragment.pokemondetails.PokeDetailsBasicFragment
import com.kerite.pokedex.ui.fragment.pokemondetails.PokeDetailsMoreFragment
import com.kerite.pokedex.ui.fragment.pokemondetails.PokeDetailsMoveFragment
import com.kerite.pokedex.viewmodel.DetailsActivityViewModel
import com.kerite.pokedex.viewmodel.PokemonDetailsMoveViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class PokeDexDetailsActivity : PokeDexBaseActivity<ActivityPokemonDetailsBinding>(
    ActivityPokemonDetailsBinding::inflate
) {
    private val menuAntiShaker = AntiShaker()
    private val viewModel: DetailsActivityViewModel by viewModels()
    private val moveViewModel: PokemonDetailsMoveViewModel by viewModels()
    private var currentDetails: PokemonDetailsEntity? = null
    private lateinit var viewPagerAdapter: SimpleFragmentViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false

        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        Timber.tag("Intent").d(intent.dataString)
        viewModel.changePokemonDexNumber(intent.getIntExtra(INTENT_DEX_NUMBER, 0))

        binding.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeButtonEnabled(true)
            }
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
            // onCreate 初始化ViewPager
            viewPagerAdapter = SimpleFragmentViewPagerAdapter(
                this@PokeDexDetailsActivity,
                R.id.detailsBasicFragment to { PokeDetailsBasicFragment() },
                R.id.detailsMoveFragment to { PokeDetailsMoveFragment() },
                R.id.detailsMoreFragment to { PokeDetailsMoreFragment() }
            )
            viewPagerAdapter.setupViewPager2(pokemonDetailsViewPager)
            pokemonDetailsViewPager.isUserInputEnabled = false
            detailsBottomNavigation.setOnItemSelectedListener {
                pokemonDetailsViewPager.currentItem = viewPagerAdapter.getPosition(it.itemId)
                true
            }
        }
    }

    private fun initPokemonDetail() {
        binding.apply {
            collapsingToolbar.title = "Pokemon Dex"
        }
    }

    private fun updateDetails(details: PokemonDetailsEntity) {
        currentDetails = details
        binding.apply {
            collapsingToolbar.title =
                if (details.formName == null) details.name else "${details.name}(${details.formName})"
            pokemonImage.load(details.imageUri)
            moveViewModel.setDexNumber(details.dexNumber)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_pokemon_details, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.action_switch_form -> {
                menuAntiShaker.antiShake {
                    SwitchFormDialogFragment().show(
                        supportFragmentManager,
                        SwitchFormDialogFragment::class.simpleName
                    )
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val INTENT_DEX_NUMBER = "pokemon_dex"
        const val INTENT_FORM_NAME = "form_name"
    }
}