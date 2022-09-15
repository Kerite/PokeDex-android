package com.kerite.pokedex.model

import com.kerite.pokedex.model.enums.PokemonRegionalVariant
import com.kerite.pokedex.model.enums.PokemonType

sealed class PokemonSearchFilter {
    object ShowAll : PokemonSearchFilter()
    data class NameFilter(val name: String) : PokemonSearchFilter()
    data class AdvancedFilterWithQuery(
        val name: String,
        val types: LinkedHashSet<PokemonType>,
        val regionalVariant: LinkedHashSet<PokemonRegionalVariant>,
        val generation: LinkedHashSet<Int>
    ) : PokemonSearchFilter()

    data class AdvancedFilter(
        val types: LinkedHashSet<PokemonType>,
        val regionalVariant: LinkedHashSet<PokemonRegionalVariant>,
        val generation: LinkedHashSet<Int>
    ) : PokemonSearchFilter()
}
