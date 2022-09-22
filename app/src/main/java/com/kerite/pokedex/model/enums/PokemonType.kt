package com.kerite.pokedex.model.enums

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kerite.pokedex.R

enum class PokemonType(
    @ColorRes val colorRes: Int,
    @StringRes val nameRes: Int,
    @DrawableRes val iconRes: Int
) {
    NORMAL(R.color.type_normal, R.string.type_normal, R.drawable.type_normal),
    FLYING(R.color.type_flying, R.string.type_flying, R.drawable.type_flying),
    FIRE(R.color.type_fire, R.string.type_fire, R.drawable.type_fire),
    PSYCHIC(R.color.type_psychic, R.string.type_psychic, R.drawable.type_psychic),
    WATER(R.color.type_water, R.string.type_water, R.drawable.type_water),
    BUG(R.color.type_bug, R.string.type_bug, R.drawable.type_bug),
    ELECTRIC(R.color.type_electric, R.string.type_electric, R.drawable.type_electric),
    ROCK(R.color.type_rock, R.string.type_rock, R.drawable.type_rock),
    GRASS(R.color.type_grass, R.string.type_grass, R.drawable.type_grass),
    GHOST(R.color.type_ghost, R.string.type_ghost, R.drawable.type_ghost),
    ICE(R.color.type_ice, R.string.type_ice, R.drawable.type_ice),
    DRAGON(R.color.type_dragon, R.string.type_dragon, R.drawable.type_dragon),
    FIGHT(R.color.type_fight, R.string.type_fighting, R.drawable.type_fighting),
    DARK(R.color.type_dark, R.string.type_dark, R.drawable.type_dark),
    POISON(R.color.type_poison, R.string.type_poison, R.drawable.type_poison),
    STEEL(R.color.type_steel, R.string.type_steel, R.drawable.type_steel),
    GROUND(R.color.type_ground, R.string.type_ground, R.drawable.type_ground),
    FAIRY(R.color.type_fairy, R.string.type_fairy, R.drawable.type_fairy);
}