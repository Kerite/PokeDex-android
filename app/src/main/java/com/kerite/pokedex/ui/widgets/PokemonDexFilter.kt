package com.kerite.pokedex.ui.widgets

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import com.kerite.pokedex.R
import com.kerite.pokedex.databinding.DialogPokemonDexFilterBinding
import com.kerite.pokedex.model.enums.PokemonRegionalVariant
import com.kerite.pokedex.model.enums.PokemonType

class PokemonDexFilter : Fragment() {
    private lateinit var binding: DialogPokemonDexFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogPokemonDexFilterBinding.inflate(layoutInflater)

        initRegionalVariantFilter()
        initTypeFilter()
        initGenerationFilter()

        return binding.root
    }

    private fun initRegionalVariantFilter() {
        for (pokemonType in PokemonRegionalVariant.values()) {
            createButton(pokemonType.displayedName, "#39c5bb", binding.gridPokemonRegionalVariant)
        }
    }

    private fun initTypeFilter() {
        for (pokemonType in PokemonType.values()) {
            createButton(pokemonType.typeName, pokemonType.color, binding.gridPokemonTypeToggle)
        }
    }

    private fun initGenerationFilter() {
        for (generation in 1..8) {
            createButton(generation.toString(), "#bbc539", binding.gridGenerationToggle)
        }
    }

    private fun createButton(name: String, color: String, parentView: GridLayout) {
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

        val toggle = SelectableTextView(requireContext())
        toggle.text = name
        toggle.setBackgroundColor(Color.parseColor(color))
        toggle.gravity = Gravity.CENTER
        toggle.setTextAppearance(R.style.SelectableTextViewText)
        toggle.width = 0
        toggle.height = 100
        toggle.isClickable = true

        parentView.addView(toggle, layoutParam)
    }
}