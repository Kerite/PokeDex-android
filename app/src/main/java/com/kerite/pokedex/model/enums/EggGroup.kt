package com.kerite.pokedex.model.enums

import androidx.annotation.StringRes
import com.kerite.pokedex.R

/**
 * 蛋组信息
 */
@Suppress("unused")
enum class EggGroup(
    @StringRes val displayedName: Int
) {
    MONSTER(R.string.egg_group_monster),
    WATER_1(R.string.egg_group_water_1),
    BUG(R.string.egg_group_bug),
    FLYING(R.string.egg_group_flying),
    FIELD(R.string.egg_group_field),
    FAIRY(R.string.egg_group_fairy),
    GRASS(R.string.egg_group_grass),
    HUMAN_LIKE(R.string.egg_group_human_like),
    WATER_3(R.string.egg_group_water_3),
    MINERAL(R.string.egg_group_mineral),
    AMORPHOUS(R.string.egg_group_amorphous),
    WATER_2(R.string.egg_group_water_2),
    DITTO(R.string.egg_group_ditto),
    DRAGON(R.string.egg_group_dragon),
    UNDISCOVERED(R.string.egg_group_undiscovered),
    GENDER_UNKNOWN(R.string.egg_group_gender_unknown);
}