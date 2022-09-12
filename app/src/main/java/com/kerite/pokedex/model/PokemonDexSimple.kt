package com.kerite.pokedex.model

import com.kerite.pokedex.model.enums.PokemonType

data class PokemonDexSimple(
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
    val type2: PokemonType?,
) {}