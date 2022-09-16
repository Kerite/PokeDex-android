package com.kerite.pokedex.ui.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.kerite.pokedex.COUNT_GENERATION
import com.kerite.pokedex.R
import com.kerite.pokedex.databinding.FragmentPokemonDexFilterBinding
import com.kerite.pokedex.model.enums.PokemonRegionalVariant
import com.kerite.pokedex.model.enums.PokemonType
import com.kerite.pokedex.ui.BaseFragment
import com.kerite.pokedex.ui.widgets.SelectableTextView
import com.kerite.pokedex.util.extension.toTypedArray
import com.kerite.pokedex.viewmodel.PokemonDexListAndFilterViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PokemonDexFilterFragment : BaseFragment<FragmentPokemonDexFilterBinding>() {
    private lateinit var pokemonDexListAndFilterViewModel: PokemonDexListAndFilterViewModel
    override fun onInitView(savedInstanceState: Bundle?) {
        Log.d("Frag", "onCreateView")
        pokemonDexListAndFilterViewModel =
            ViewModelProvider(requireActivity())[PokemonDexListAndFilterViewModel::class.java]

        initRegionalVariantFilter(pokemonDexListAndFilterViewModel.filterRegionalVariant.value)
        initTypeFilter(pokemonDexListAndFilterViewModel.filterType.value)
        initGenerationFilter(pokemonDexListAndFilterViewModel.filterGeneration.value)

//        lifecycleScope.launch {
//            pokemonDexListAndFilterViewModel.advancedFilterMode.collectLatest {
//                binding.root.visibility = if (it) View.VISIBLE else View.GONE
//            }
//        }
    }

    private fun initRegionalVariantFilter(
        types: Set<PokemonRegionalVariant> = setOf(*PokemonRegionalVariant.values())
    ) {
        for (pokemonRegionalVariant in PokemonRegionalVariant.values()) {
            val button = createButton(
                pokemonRegionalVariant.displayedName,
                "#39c5bb",
                binding.gridPokemonRegionalVariant,
                pokemonRegionalVariant,
                types.contains(pokemonRegionalVariant)
            ) {
                Log.d(
                    "RegionalVariantButton",
                    "${pokemonRegionalVariant.displayedName} checked $it"
                )
                if (it) {
                    pokemonDexListAndFilterViewModel.addRegionalVariant(pokemonRegionalVariant)
                } else {
                    pokemonDexListAndFilterViewModel.removeRegionalVariant(pokemonRegionalVariant)
                }
            }
        }
    }

    private fun initTypeFilter(types: Set<PokemonType> = setOf(*PokemonType.values())) {
        for (pokemonType in PokemonType.values()) {
            val button = createButton(
                pokemonType.typeName,
                pokemonType.color,
                binding.gridPokemonTypeToggle,
                pokemonType,
                types.contains(pokemonType)
            ) {
                if (it) {
                    pokemonDexListAndFilterViewModel.addPokemonType(pokemonType)
                } else {
                    pokemonDexListAndFilterViewModel.removePokemonType(pokemonType)
                }
            }
        }
    }

    private fun initGenerationFilter(
        generations: Set<Int> = setOf(*(1..COUNT_GENERATION).toTypedArray())
    ) {
        for (generation in (1..COUNT_GENERATION).toTypedArray()) {
            val button = createButton(
                generation.toString(),
                "#bbc539",
                binding.gridGenerationToggle,
                generation,
                generations.contains(generation)
            ) {
                if (it) {
                    pokemonDexListAndFilterViewModel.addGeneration(generation)
                } else {
                    pokemonDexListAndFilterViewModel.removeGeneration(generation)
                }
            }
        }
    }

    private fun <T> createButton(
        name: String,
        color: String,
        parentView: GridLayout,
        mappedData: T,
        checked: Boolean,
        checkedChangeListener: (Boolean) -> Unit
    ): SelectableTextView<T> {
        val layoutParam =
            GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f)
            )
//            layoutParam.setGravity(Gravity.FILL)
        layoutParam.marginStart = 30
        layoutParam.marginEnd = 30
        layoutParam.bottomMargin = 20
        layoutParam.topMargin = 20

        val toggle = SelectableTextView<T>(requireContext())
        toggle.text = name
        toggle.setBackgroundColor(Color.parseColor(color))
        toggle.gravity = Gravity.CENTER
        toggle.setTextAppearance(R.style.SelectableTextViewText)
        toggle.width = 0
        toggle.height = 100
        toggle.isClickable = true
        toggle.setOnCheckedChanged(checkedChangeListener)
        toggle.mappedData = mappedData
        toggle.checked = checked

        parentView.addView(toggle, layoutParam)
        return toggle
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d("Frag", "onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }

    override fun onAttach(context: Context) {
        Log.d("Frag", "onAttach")
        super.onAttach(context)
    }

    override fun onResume() {
        Log.d("Frag", "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("Frag", "onPause")
        super.onPause()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.d("Frag", "onViewStateRestored")
//        onInitView(savedInstanceState)
        super.onViewStateRestored(savedInstanceState)
    }

    companion object {
        val FULL_GENERATIONS = (1..COUNT_GENERATION).toTypedArray()
    }
}