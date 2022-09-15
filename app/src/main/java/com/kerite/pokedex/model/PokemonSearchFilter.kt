package com.kerite.pokedex.model

import com.kerite.pokedex.model.enums.PokemonRegionalVariant
import com.kerite.pokedex.model.enums.PokemonType

data class PokemonSearchFilter(
    val name: String,
    val types: Array<PokemonType>,
    val regionalVariant: Array<PokemonRegionalVariant>,
    val generation: Array<Int>
) {}