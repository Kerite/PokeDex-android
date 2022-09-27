package com.kerite.pokedex.ui.activity

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.textview.MaterialTextView
import com.kerite.pokedex.database.entity.PokemonDetailsEntity
import com.kerite.pokedex.databinding.ActivityPokemonDetailsBinding
import com.kerite.pokedex.model.PokemonDetails
import com.kerite.pokedex.model.enums.EggGroup
import com.kerite.pokedex.ui.BaseActivity
import com.kerite.pokedex.ui.dialog.AbilityDialogFragment
import com.kerite.pokedex.ui.dialog.EggGroupDialogFragment
import com.kerite.pokedex.util.AntiShaker
import com.kerite.pokedex.viewmodel.DetailsActivityViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class PokeDexDetailsActivity : BaseActivity<ActivityPokemonDetailsBinding>(
    ActivityPokemonDetailsBinding::inflate
), OnClickListener {
    private lateinit var viewModel: DetailsActivityViewModel
    private var currentDetails: PokemonDetailsEntity? = null
    private val dialogAntiShaker: AntiShaker = AntiShaker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailsActivityViewModel::class.java]
        viewModel.changePokemonDexNumber(intent.getIntExtra(INTENT_DEX_NUMBER, 0))

        binding.apply {
            setSupportActionBar(toolbar)

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            abilitySubview.ability1.setOnClickListener(this@PokeDexDetailsActivity)
            abilitySubview.ability2.setOnClickListener(this@PokeDexDetailsActivity)
            abilitySubview.abilityHidden.setOnClickListener(this@PokeDexDetailsActivity)

            eggGroupCard.pokemonEggGroupTextView1.setOnClickListener(this@PokeDexDetailsActivity)
            eggGroupCard.pokemonEggGroupTextView2.setOnClickListener(this@PokeDexDetailsActivity)
            eggGroupCard.pokemonEggCycleTextView.setOnClickListener(this@PokeDexDetailsActivity)

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
            lifecycleScope.launch {
                viewModel.pokemonDetails.collect {
                    pokemonSubnameTab.removeAllTabs()
                    for (detail in it) {
                        pokemonSubnameTab.addTab(pokemonSubnameTab.newTab().setText(detail.formName ?: detail.name))
                    }
                }
            }
            pokemonSubnameTab.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) {
                        viewModel.changePokemonIndex(tab.position)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
                override fun onTabReselected(tab: TabLayout.Tab?) = Unit
            })
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
        Timber.tag("PokemonChanged").d(details.toString())
        currentDetails = details
        binding.apply {
            collapsingToolbar.title = details.name
            pokemonDexNumber.text = "#" + details.dexNumber.toString()
            pokemonJpName.text = details.jpName
            pokemonEnName.text = details.enName
//            CoroutineScope(Dispatchers.IO).launch {
//                val imageUrl = Uri.parse(
//                    "file:///android_asset/images/" + decidePicName(details.dexNumber, details.formName, details.name)
//                )
//                Timber.d("Load Image" + imageUrl.path)
//
//            }
            val text = "file:///android_asset/images/${details.dexNumber}#${details.name}#${details.formName ?: ""}#.webp".replace("##", "#")
            Timber.tag("LoadImage").d(text)
            Glide.with(this@PokeDexDetailsActivity)
                .load(Uri.parse(text))
                .into(pokemonImage)
//            if (imageLoadDisposable != null) {
//                imageLoadDisposable!!.dispose()
//            }
//            imageLoadDisposable = pokemonImage.load(Uri.parse(text))
//            Glide.with(this@PokemonDetailsActivity)
//                .load(Uri.parse(text))
//                .into(pokemonImage)
            pokemonHeight.text = details.height + " m"
            pokemonWeight.text = details.weight + " kg"

            abilitySubview.ability1.text = details.ability1
            abilitySubview.ability2.text = details.ability2
            abilitySubview.abilityHidden.text = details.abilityHidden

            eggGroupCard.pokemonEggGroupTextView1.text =
                resources.getString(details.eggGroup1.displayedName)
            eggGroupCard.pokemonEggGroupTextView2.text = if (details.eggGroup2 != null)
                resources.getString(details.eggGroup2.displayedName) else ""
            eggGroupCard.pokemonEggCycleTextView.text = details.eggCycle.toString()

            pokemonType1.type = details.type1
            if (details.type2 != null) {
                pokemonType2.visibility = View.VISIBLE
                pokemonTypeSpace.visibility = View.VISIBLE
                pokemonType2.type = details.type2
            } else {
                pokemonType2.visibility = View.GONE
                pokemonTypeSpace.visibility = View.GONE
            }

            pokemonBodyImage.setImageResource(details.body.icon)
            pokemonBody.setText(details.body.displayedName)
            catchRate.text = details.catchRate.toString()

            //<editor-fold desc="读取努力值" defaultstate="collapsed">
            evHp.text = details.evHp.toString()
            evAttack.text = details.evAttack.toString()
            evDefence.text = details.evDefence.toString()
            evSpecialAttack.text = details.evSpecialAttack.toString()
            evSpecialDefence.text = details.evSpecialDefence.toString()
            evSpeed.text = details.evSpeed.toString()
            //</editor-fold>

            //<editor-fold desc="读取种族值" defaultstate="collapsed">
            pokemonStrength.hp = details.hp
            pokemonStrength.attack = details.attack
            pokemonStrength.defence = details.defence
            pokemonStrength.specialAttack = details.specialAttack
            pokemonStrength.specialDefence = details.specialDefence
            pokemonStrength.speed = details.speed
            //</editor-fold>
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.abilitySubview.ability1.id,
            binding.abilitySubview.ability2.id,
            binding.abilitySubview.abilityHidden.id -> {
                val textView = v as MaterialTextView
                if (textView.text.isNotBlank()) {
                    showAbilityDialog(textView.text)
                }
            }

            binding.eggGroupCard.pokemonEggGroupTextView1.id -> {
                currentDetails?.apply {
                    showEggGroupDialog(eggGroup1)
                }
            }

            binding.eggGroupCard.pokemonEggGroupTextView2.id -> {
                currentDetails?.eggGroup2?.apply {
                    showEggGroupDialog(this)
                }
            }

            binding.eggGroupCard.pokemonEggCycleTextView.id -> {
            }
        }
    }

    private fun showAbilityDialog(abilityString: CharSequence) {
        dialogAntiShaker.antiShake {
            AbilityDialogFragment(abilityString.toString()).show(
                this.supportFragmentManager, AbilityDialogFragment::javaClass.name
            )
        }
    }

    private fun showEggGroupDialog(eggGroup: EggGroup) {
        dialogAntiShaker.antiShake {
            EggGroupDialogFragment(eggGroup).show(
                supportFragmentManager, EggGroupDialogFragment::javaClass.name
            )
        }
    }

    companion object {
        const val INTENT_DEX_NUMBER = "pokemon_dex"
        const val INTENT_FORM_NAME = "form_name"
    }
}