package com.kerite.pokedex.ui.fragment.pokemondetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.kerite.fission.android.ui.BaseFragment
import com.kerite.pokedex.databinding.FragmentPokemonDetailsMoreBinding
import com.kerite.pokedex.model.PokemonDetails
import com.kerite.pokedex.viewmodel.DetailsActivityViewModel
import kotlinx.coroutines.launch

class PokeDetailsMoreFragment : BaseFragment<FragmentPokemonDetailsMoreBinding>(
    FragmentPokemonDetailsMoreBinding::inflate
) {
    private val detailsActivityViewModel: DetailsActivityViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            detailsActivityViewModel.currentPokemonDetail.collect {
                when (it) {
                    is PokemonDetails.Found ->
                        binding.pokemonDamageMultiplierWidget.pokemonTypes =
                            it.details.type1.flag or (it.details.type2?.flag ?: 0)
                    is PokemonDetails.None ->
                        binding.pokemonDamageMultiplierWidget.pokemonTypes = 0
                }
            }
        }
    }
}