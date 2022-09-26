package com.kerite.pokedex.ui.dialog

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kerite.pokedex.database.PokemonDatabase
import com.kerite.pokedex.databinding.FragmentDialogEgggroupBinding
import com.kerite.pokedex.model.enums.EggGroup
import com.kerite.pokedex.recyclers.EggGroupDialogRecyclerAdapter
import com.kerite.pokedex.ui.BaseBottomDialogFragment
import com.kerite.pokedex.ui.activity.PokeDexDetailsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EggGroupDialogFragment(
        private val eggGroup: EggGroup
) : BaseBottomDialogFragment<FragmentDialogEgggroupBinding>(
        FragmentDialogEgggroupBinding::inflate, false
) {
    private lateinit var recyclerAdapter: EggGroupDialogRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            recyclerView.apply {
                recyclerAdapter = EggGroupDialogRecyclerAdapter(context) {
                    val intent = Intent(context, PokeDexDetailsActivity::class.java)
                    intent.putExtra(PokeDexDetailsActivity.INTENT_DEX_NUMBER, it.dexNumber)
                    intent.putExtra(PokeDexDetailsActivity.INTENT_FORM_NAME, it.formName)
                    startActivity(intent)
                }
                adapter = recyclerAdapter
                layoutManager = LinearLayoutManager(requireContext())
                lifecycleScope.launchWhenResumed {
                    val detailsList = withContext(Dispatchers.IO) {
                        PokemonDatabase.getInstance(requireContext())
                                .pokemonDetailsDao()
                                .filterByEggGroup(eggGroup)
                    }
                    recyclerAdapter.submitList(detailsList)
                }
            }
            eggGroupTitleTextView.text = resources.getString(eggGroup.displayedName)
        }
    }
}