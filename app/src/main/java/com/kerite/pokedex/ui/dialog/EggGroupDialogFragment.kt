package com.kerite.pokedex.ui.dialog

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kerite.fission.android.extensions.startActivity
import com.kerite.fission.android.ui.SimpleListRecyclerAdapter
import com.kerite.pokedex.database.PokemonDatabase
import com.kerite.pokedex.database.entity.PokemonDetailsEntity
import com.kerite.pokedex.databinding.FragmentDialogEgggroupBinding
import com.kerite.pokedex.databinding.ItemEggGroupDialogBinding
import com.kerite.pokedex.model.enums.EggGroup
import com.kerite.pokedex.ui.BaseBottomDialogFragment
import com.kerite.pokedex.ui.activity.PokeDexDetailsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EggGroupDialogFragment(
    private val eggGroup: EggGroup
) : BaseBottomDialogFragment<FragmentDialogEgggroupBinding>(
    FragmentDialogEgggroupBinding::inflate, false
) {
    private lateinit var recyclerAdapter: SimpleListRecyclerAdapter<ItemEggGroupDialogBinding, PokemonDetailsEntity>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            recyclerView.apply {
                recyclerAdapter = SimpleListRecyclerAdapter(
                    context, ItemEggGroupDialogBinding::inflate,
                    onBind = {
                        pokemonNameTextView.text = it.name
                        pokemonFormName.text = it.formName
                        val headerImagePath =
                            "file:///android_asset/images/${it.dexNumber}#${it.name}#${it.formName ?: ""}#.webp"
                                .replace("##", "#")
                        val headerUri = Uri.parse(headerImagePath)
                        Glide.with(root)
                            .load(headerUri)
                            .into(pokemonThumbnail)
                    },
                    onItemClick = {
                        startActivity<PokeDexDetailsActivity>(
                            PokeDexDetailsActivity.INTENT_DEX_NUMBER to it.dexNumber,
                            PokeDexDetailsActivity.INTENT_FORM_NAME to it.formName
                        )
                    }
                )
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