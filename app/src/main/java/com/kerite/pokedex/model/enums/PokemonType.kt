package com.kerite.pokedex.model.enums

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kerite.pokedex.R

enum class PokemonType(
    @ColorRes val colorRes: Int,
    @StringRes val nameRes: Int,
    @DrawableRes val iconRes: Int,
    val flag: Int
) {
    NORMAL(R.color.type_normal, R.string.type_normal, R.drawable.type_normal, 1),
    FIGHTING(R.color.type_fight, R.string.type_fighting, R.drawable.type_fighting, 1 shl 1),
    FLYING(R.color.type_flying, R.string.type_flying, R.drawable.type_flying, 1 shl 2),
    POISON(R.color.type_poison, R.string.type_poison, R.drawable.type_poison, 1 shl 3),
    GROUND(R.color.type_ground, R.string.type_ground, R.drawable.type_ground, 1 shl 4),
    ROCK(R.color.type_rock, R.string.type_rock, R.drawable.type_rock, 1 shl 5),
    BUG(R.color.type_bug, R.string.type_bug, R.drawable.type_bug, 1 shl 6),
    GHOST(R.color.type_ghost, R.string.type_ghost, R.drawable.type_ghost, 1 shl 7),
    STEEL(R.color.type_steel, R.string.type_steel, R.drawable.type_steel, 1 shl 8),
    FIRE(R.color.type_fire, R.string.type_fire, R.drawable.type_fire, 1 shl 9),
    WATER(R.color.type_water, R.string.type_water, R.drawable.type_water, 1 shl 10),
    GRASS(R.color.type_grass, R.string.type_grass, R.drawable.type_grass, 1 shl 11),
    ELECTRIC(R.color.type_electric, R.string.type_electric, R.drawable.type_electric, 1 shl 12),
    PSYCHIC(R.color.type_psychic, R.string.type_psychic, R.drawable.type_psychic, 1 shl 13),
    ICE(R.color.type_ice, R.string.type_ice, R.drawable.type_ice, 1 shl 14),
    DRAGON(R.color.type_dragon, R.string.type_dragon, R.drawable.type_dragon, 1 shl 15),
    DARK(R.color.type_dark, R.string.type_dark, R.drawable.type_dark, 1 shl 16),
    FAIRY(R.color.type_fairy, R.string.type_fairy, R.drawable.type_fairy, 1 shl 17);

    companion object {
        val values by lazy(LazyThreadSafetyMode.PUBLICATION) { values() }
    }
}