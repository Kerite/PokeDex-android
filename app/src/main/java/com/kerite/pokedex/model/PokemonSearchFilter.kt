package com.kerite.pokedex.model

import com.kerite.pokedex.model.enums.PokemonRegionalVariant
import com.kerite.pokedex.model.enums.PokemonType

sealed class PokemonSearchFilter {
    object ShowAll : PokemonSearchFilter()
    data class NameFilter(val name: String) : PokemonSearchFilter()
    data class AdvancedFilterWithQuery(
        val name: String,
        val types: Set<PokemonType>,
        val regionalVariant: Set<PokemonRegionalVariant>,
        val generation: Set<Int>
    ) : PokemonSearchFilter()

    data class AdvancedFilter(
        val types: Set<PokemonType>,
        val regionalVariant: Set<PokemonRegionalVariant>,
        val generation: Set<Int>
    ) : PokemonSearchFilter()
}
