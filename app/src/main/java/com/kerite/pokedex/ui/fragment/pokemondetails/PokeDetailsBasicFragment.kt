package com.kerite.pokedex.ui.fragment.pokemondetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.textview.MaterialTextView
import com.kerite.fission.AntiShaker
import com.kerite.fission.android.ui.BaseFragment
import com.kerite.pokedex.database.entity.PokemonDetailsEntity
import com.kerite.pokedex.databinding.FragmentPokemonDetailsBasicBinding
import com.kerite.pokedex.model.PokemonDetails
import com.kerite.pokedex.model.enums.EggGroup
import com.kerite.pokedex.ui.dialog.AbilityDialogFragment
import com.kerite.pokedex.ui.dialog.EggGroupDialogFragment
import com.kerite.pokedex.viewmodel.DetailsActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokeDetailsBasicFragment : BaseFragment<FragmentPokemonDetailsBasicBinding>(
    FragmentPokemonDetailsBasicBinding::inflate
), View.OnClickListener {
    private val detailsActivityViewModel: DetailsActivityViewModel by activityViewModels()
    private val dialogAntiShaker: AntiShaker = AntiShaker()
    private var currentDetails: PokemonDetailsEntity? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            abilitySubview.ability1.setOnClickListener(this@PokeDetailsBasicFragment)
            abilitySubview.ability2.setOnClickListener(this@PokeDetailsBasicFragment)
            abilitySubview.abilityHidden.setOnClickListener(this@PokeDetailsBasicFragment)

            eggGroupCard.pokemonEggGroupTextView1.setOnClickListener(this@PokeDetailsBasicFragment)
            eggGroupCard.pokemonEggGroupTextView2.setOnClickListener(this@PokeDetailsBasicFragment)
            eggGroupCard.pokemonEggCycleTextView.setOnClickListener(this@PokeDetailsBasicFragment)

            lifecycleScope.launch {
                detailsActivityViewModel.currentPokemonDetail.collect {
                    when (it) {
                        is PokemonDetails.Found ->
                            updateDetails(it.details)

                        is PokemonDetails.None ->
                            initDetails()
                    }
                }
            }
        }
    }

    private suspend fun updateDetails(details: PokemonDetailsEntity) {
        currentDetails = details
        withContext(Dispatchers.Main) {
            binding.apply {
                pokemonDexNumber.text = "#" + details.dexNumber.toString()
                pokemonJpName.text = details.jpName
                pokemonEnName.text = details.enName
                pokemonHeight.text = details.height + " m"
                pokemonWeight.text = details.weight + " kg"
                pokemonType1.type = details.type1
                pokemonBodyImage.load(details.body.icon)
                pokemonBody.setText(details.body.displayedName)
                catchRate.text = details.catchRate.toString()
                if (details.type2 != null) {
                    pokemonType2.visibility = View.VISIBLE
                    pokemonTypeSpace.visibility = View.VISIBLE
                    pokemonType2.type = details.type2
                } else {
                    pokemonType2.visibility = View.GONE
                    pokemonTypeSpace.visibility = View.GONE
                }

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

                abilitySubview.ability1.text = details.ability1
                abilitySubview.ability2.text = details.ability2
                abilitySubview.abilityHidden.text = details.abilityHidden

                eggGroupCard.pokemonEggGroupTextView1.text =
                    resources.getString(details.eggGroup1.displayedName)
                eggGroupCard.pokemonEggGroupTextView2.text = if (details.eggGroup2 != null)
                    resources.getString(details.eggGroup2.displayedName) else ""
                eggGroupCard.pokemonEggCycleTextView.text = details.eggCycle.toString()
            }
        }
    }

    private suspend fun initDetails() {
        withContext(Dispatchers.Main) {
            binding.apply {
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
                requireActivity().supportFragmentManager, AbilityDialogFragment::javaClass.name
            )
        }
    }

    private fun showEggGroupDialog(eggGroup: EggGroup) {
        dialogAntiShaker.antiShake {
            EggGroupDialogFragment(eggGroup).show(
                requireActivity().supportFragmentManager, EggGroupDialogFragment::javaClass.name
            )
        }
    }
}