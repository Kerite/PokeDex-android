package com.kerite.pokedex.ui.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kerite.fission.android.ui.SimpleListRecyclerAdapter
import com.kerite.pokedex.database.entity.PokemonDetailsEntity
import com.kerite.pokedex.databinding.FragmentDialogPokemonFormSelectBinding
import com.kerite.pokedex.databinding.ItemPokemonListBinding
import com.kerite.pokedex.ui.BaseBottomDialogFragment
import com.kerite.pokedex.viewmodel.DetailsActivityViewModel

class SwitchFormDialogFragment(

) : BaseBottomDialogFragment<FragmentDialogPokemonFormSelectBinding>(
    FragmentDialogPokemonFormSelectBinding::inflate
) {
    private val detailsActivityViewModel: DetailsActivityViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerAdapter =
            SimpleListRecyclerAdapter<ItemPokemonListBinding, PokemonDetailsEntity>(
                requireContext(), ItemPokemonListBinding::inflate,
                onBind = {
                    pokemonFormName.text = it.formName ?: it.name
                },
                onItemClick = {
                    detailsActivityViewModel.changePokemonIndex(it.formName)
                    this@SwitchFormDialogFragment.dismiss()
                }
            )
        binding.pokemonListRecyclerView.adapter = recyclerAdapter
        binding.pokemonListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        lifecycleScope.launchWhenResumed {
            detailsActivityViewModel.pokemonDetails.collect {
                recyclerAdapter.submitList(it)
            }
        }
    }
}