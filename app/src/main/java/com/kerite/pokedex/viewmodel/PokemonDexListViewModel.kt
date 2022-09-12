package com.kerite.pokedex.viewmodel

import com.kerite.pokedex.model.PokemonDex
import com.kerite.pokedex.model.enums.PokemonType

data class PokemonDexListViewModel(
    /**
     * 图鉴编号
     */
    val dexNumber: Int,
    /**
     * 名字
     */
    val name: String,
    /**
     * 第一属性
     */
    val type1: PokemonType,
    /**
     * 第二属性
     */
    val type2: PokemonType? = null,
) {
    constructor(pokemonDex: PokemonDex) : this(
        pokemonDex.dexNumber,
        pokemonDex.name,
        pokemonDex.type1,
        pokemonDex.type2
    )
}