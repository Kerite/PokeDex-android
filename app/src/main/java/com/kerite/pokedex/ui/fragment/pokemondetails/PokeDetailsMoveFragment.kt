package com.kerite.pokedex.ui.fragment.pokemondetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.kerite.fission.android.ui.BaseFragment
import com.kerite.fission.android.ui.SimpleListRecyclerAdapter
import com.kerite.pokedex.databinding.FragmentPokemonDetailsMoveBinding
import com.kerite.pokedex.databinding.ItemMoveListBinding
import com.kerite.pokedex.model.PokemonMoveRow
import com.kerite.pokedex.model.enums.MovePattern
import com.kerite.pokedex.viewmodel.PokemonDetailsMoveViewModel
import kotlinx.coroutines.launch

class PokeDetailsMoveFragment : BaseFragment<FragmentPokemonDetailsMoveBinding>(
    FragmentPokemonDetailsMoveBinding::inflate
) {
    private val moveViewModel: PokemonDetailsMoveViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerAdapter = SimpleListRecyclerAdapter<ItemMoveListBinding, PokemonMoveRow>(
            requireContext(), ItemMoveListBinding::inflate,
            onBind = {
                if (it.pattern == MovePattern.TEACH) {
                    moveLevelOrTmView.visibility = View.GONE
                } else {
                    moveLevelOrTmView.text = it.value
                }
                moveAccuracyView.text = it.accuracy.getDisplayedText(requireContext())
                moveDescriptionView.text = it.description
                moveNameView.text = it.name
                moveTypeIcon.type = it.type
                movePowerView.text = it.power.getDisplayedText(requireContext())
            }
        )

        binding.moveListView.adapter = recyclerAdapter
        binding.moveListView.isNestedScrollingEnabled = false

        lifecycleScope.launch {
            moveViewModel.moveList.collect {
                recyclerAdapter.submitList(it)
            }
        }
    }

    companion object {
        const val STATE_MOVE_PATTERN = "move_pattern"
        const val STATE_DEX_NUMBER = "dex_number"
        const val STATE_FORM_NAME = "form_name"
    }
}