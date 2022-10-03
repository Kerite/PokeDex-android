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
import com.kerite.pokedex.databinding.FragmentDialogAbilityBinding
import com.kerite.pokedex.databinding.ItemAbilityDialogBinding
import com.kerite.pokedex.ui.BaseBottomDialogFragment
import com.kerite.pokedex.ui.activity.PokeDexDetailsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class AbilityDialogFragment(
    private val abilityName: String
) : BaseBottomDialogFragment<FragmentDialogAbilityBinding>(
    FragmentDialogAbilityBinding::inflate, false
) {
    private lateinit var recyclerAdapter: SimpleListRecyclerAdapter<ItemAbilityDialogBinding, PokemonDetailsEntity>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.d(abilityName)
        binding.apply {
            pokemonList.apply {
                recyclerAdapter = SimpleListRecyclerAdapter(
                    context, ItemAbilityDialogBinding::inflate,
                    onBind = {
                        Timber.tag("Performance").d("Binding PokemonDetails")
                        pokemonName.text = it.name
                        pokemonFormName.text = it.formName
                        val headerImagePath =
                            "file:///android_asset/images/${it.dexNumber}#${it.name}#${it.formName ?: ""}#.webp"
                                .replace("##", "#")
                        val headerUri = Uri.parse(headerImagePath)
                        Glide.with(binding.root)
                            .load(headerUri)
                            .into(pokemonImage)
                    },
                    onItemClick = {
                        startActivity<PokeDexDetailsActivity>(
                            PokeDexDetailsActivity.INTENT_DEX_NUMBER to it.dexNumber,
                            PokeDexDetailsActivity.INTENT_FORM_NAME to (it.formName ?: ""),
                        )
                    }
                )
                this.adapter = recyclerAdapter
                layoutManager = LinearLayoutManager(context)
                lifecycleScope.launchWhenResumed {
                    val db = PokemonDatabase.getInstance(requireContext())
                    val detailsList = withContext(Dispatchers.IO) {
                        db.pokemonDetailsDao()
                            .filterByAbility(abilityName)
                    }
                    recyclerAdapter.submitList(detailsList)
                }
            }
            lifecycleScope.launchWhenResumed {
                val db = PokemonDatabase.getInstance(requireContext())
                val abilityInfo = withContext(Dispatchers.IO) {
                    db.pokemonAbilitySummary()
                        .findByAbilityName(abilityName)
                }
                abilityNameTextView.text = abilityInfo?.name
                abilityDescriptionTextView.text = abilityInfo?.description
            }
        }
    }
}