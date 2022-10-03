package com.kerite.pokedex.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.kerite.fission.android.ui.BaseFragment
import com.kerite.fission.android.ui.SimpleListRecyclerAdapter
import com.kerite.pokedex.databinding.FragmentMoveLearnListBinding
import com.kerite.pokedex.databinding.ItemMoveListBinding
import com.kerite.pokedex.model.PokemonMoveRow
import com.kerite.pokedex.viewmodel.MoveLearnListViewModel
import kotlinx.coroutines.launch

class MoveLearnListFragment : BaseFragment<FragmentMoveLearnListBinding>(
    FragmentMoveLearnListBinding::inflate
) {
    private val viewModel: MoveLearnListViewModel by activityViewModels()
    private lateinit var recyclerAdapter: SimpleListRecyclerAdapter<ItemMoveListBinding, PokemonMoveRow>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        binding.apply {
            lifecycleScope.launch {
                viewModel.moveLearnList.collect {
                    recyclerAdapter.submitList(it)
                }
            }
        }
    }

    fun showMoveDetailsDialog() {

    }

    private fun setupRecyclerView() {
        binding.moveListView.apply {
            recyclerAdapter = SimpleListRecyclerAdapter(
                context, ItemMoveListBinding::inflate,
                onItemClick = { },
                onBind = {
                    moveLevelOrTmView.text = it.value
                    moveAccuracyView.text = it.accuracy.getDisplayedText(context)
                    movePowerView.text = it.power.getDisplayedText(context)
                    moveNameView.text = it.name
                    moveDescriptionView.text = it.description
                    moveTypeIcon.type = it.type
                }
            )
            adapter = recyclerAdapter
        }
    }
}