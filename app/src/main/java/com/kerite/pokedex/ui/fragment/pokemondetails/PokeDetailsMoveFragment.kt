package com.kerite.pokedex.ui.fragment.pokemondetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import com.kerite.fission.android.ui.BaseFragment
import com.kerite.fission.android.ui.SimpleListRecyclerAdapter
import com.kerite.pokedex.databinding.FragmentPokemonDetailsMoveBinding
import com.kerite.pokedex.databinding.ItemMoveListBinding
import com.kerite.pokedex.model.PokemonMoveRow
import com.kerite.pokedex.model.enums.MovePattern
import com.kerite.pokedex.ui.MoveItemAnimator
import com.kerite.pokedex.viewmodel.PokemonDetailsMoveViewModel
import kotlinx.coroutines.launch

class PokeDetailsMoveFragment : BaseFragment<FragmentPokemonDetailsMoveBinding>(
    FragmentPokemonDetailsMoveBinding::inflate
) {
    private val moveViewModel: PokemonDetailsMoveViewModel by activityViewModels()
    private lateinit var recyclerAdapter: SimpleListRecyclerAdapter<ItemMoveListBinding, PokemonMoveRow>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerAdapter = SimpleListRecyclerAdapter(
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
            },
            diffCallback = object : DiffUtil.ItemCallback<PokemonMoveRow>() {
                override fun areItemsTheSame(
                    oldItem: PokemonMoveRow,
                    newItem: PokemonMoveRow
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: PokemonMoveRow,
                    newItem: PokemonMoveRow
                ): Boolean {
                    return oldItem == newItem
                }
            },
            animRes = android.R.anim.slide_in_left
        )
        recyclerAdapter.animateEnabled = false
        binding.moveListView.adapter = recyclerAdapter
        binding.moveListView.isNestedScrollingEnabled = false
        binding.movePatternSelector.check(binding.selectorLevelUpButton.id)
        binding.moveListView.itemAnimator = MoveItemAnimator()

        binding.selectorLevelUpButton.setOnClickListener {
            changeMovePattern(MovePattern.LEVEL)
        }
        binding.selectorBreedButton.setOnClickListener {
            changeMovePattern(MovePattern.BREED)
        }
        binding.selectorTmButton.setOnClickListener {
            changeMovePattern(MovePattern.TM_MACHINE)
        }
        binding.selectorTutorButton.setOnClickListener {
            changeMovePattern(MovePattern.TEACH)
        }
        lifecycleScope.launch {
            moveViewModel.moveList.collect {
                recyclerAdapter.submitList(it) {
                    binding.moveListView.post {
                        binding.moveLoadingProgressView.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun changeMovePattern(pattern: MovePattern) {
        recyclerAdapter.submitList(null) {
            binding.moveListView.post {
                binding.moveLoadingProgressView.visibility = View.VISIBLE
            }
        }
        moveViewModel.setMovePattern(pattern)
    }

    companion object {
        const val STATE_MOVE_PATTERN = "move_pattern"
        const val STATE_DEX_NUMBER = "dex_number"
        const val STATE_FORM_NAME = "form_name"
    }
}