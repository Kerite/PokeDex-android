package com.kerite.pokedex.model

import com.kerite.pokedex.database.entity.PokemonDetailsEntity

sealed class PokemonDetails {
    object None : PokemonDetails()
    data class Found(val details: PokemonDetailsEntity) : PokemonDetails()
}
