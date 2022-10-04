package com.kerite.pokedex.ui.dialog

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import androidx.annotation.ColorRes
import androidx.fragment.app.activityViewModels
import com.kerite.pokedex.COUNT_GENERATION
import com.kerite.pokedex.R
import com.kerite.pokedex.databinding.FragmentPokemonDexFilterBinding
import com.kerite.pokedex.model.enums.PokemonRegionalVariant
import com.kerite.pokedex.model.enums.PokemonType
import com.kerite.pokedex.ui.BaseBottomDialogFragment
import com.kerite.pokedex.ui.widgets.SelectableTextView
import com.kerite.pokedex.util.extension.toTypedArray
import com.kerite.pokedex.viewmodel.PokemonDexListAndFilterViewModel

class PokeDexFilterBottomDialogFragment : BaseBottomDialogFragment<FragmentPokemonDexFilterBinding>(
    FragmentPokemonDexFilterBinding::inflate
) {
    private val pokemonDexListAndFilterViewModel: PokemonDexListAndFilterViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRegionalVariantFilter(pokemonDexListAndFilterViewModel.filterRegionalVariant.value)
        initTypeFilter(pokemonDexListAndFilterViewModel.filterType.value)
        initGenerationFilter(pokemonDexListAndFilterViewModel.filterGeneration.value)
    }

    //region 初始化视图
    private fun initRegionalVariantFilter(
        types: Set<PokemonRegionalVariant> = setOf(*PokemonRegionalVariant.values())
    ) {
        for (pokemonRegionalVariant in PokemonRegionalVariant.values()) {
            val button = createButton(
                pokemonRegionalVariant.displayedName,
                R.color.regional_variant_background,
                binding.gridPokemonRegionalVariant,
                pokemonRegionalVariant,
                types.contains(pokemonRegionalVariant)
            ) {
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
                resources.getString(pokemonType.nameRes),
                pokemonType.colorRes,
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
                R.color.generation_button_background,
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
        @ColorRes color: Int,
        parentView: GridLayout,
        mappedData: T,
        checked: Boolean,
        checkedChangeListener: (Boolean) -> Unit
    ): SelectableTextView<T> {
        val layoutParam = GridLayout.LayoutParams(
            GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)
        )
        layoutParam.marginStart = 30
        layoutParam.marginEnd = 30
        layoutParam.bottomMargin = 20
        layoutParam.topMargin = 20

        val toggle = SelectableTextView<T>(requireContext())
        toggle.text = name
        toggle.setBackgroundResource(color)
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
    //endregion

    companion object {
        val FULL_GENERATIONS = (1..COUNT_GENERATION).toTypedArray()
    }
}