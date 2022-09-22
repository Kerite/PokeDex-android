package com.kerite.pokedex.model.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kerite.pokedex.R

/**
 * 宝可梦体形列表 [资料来源](https://wiki.52poke.com/wiki/宝可梦列表（按体形分类）)
 * @param displayedName 体形的名称
 * @param icon 体形的图标
 */
enum class PokemonBody(
    @StringRes val displayedName: Int,
    @DrawableRes val icon: Int
) {
    BALL(
        R.string.body_ball,
        R.drawable.body_ball
    ),
    SNAKE(
        R.string.body_snake,
        R.drawable.body_snake
    ),
    FISH(
        R.string.body_fish,
        R.drawable.body_fish
    ),
    DUAL_HANDS(
        R.string.body_dual_hands,
        R.drawable.body_dual_hands
    ),
    PILLAR(
        R.string.body_pillar,
        R.drawable.body_pillar
    ),
    DUAL_FOOTS_MONSTER(
        R.string.body_dual_foots_monster,
        R.drawable.body_dual_foots_monster
    ),
    DUAL_LEGS(
        R.string.body_dual_legs,
        R.drawable.body_dual_legs
    ),
    QUADRUPLE_FOOTS_MONSTER(
        R.string.body_quadruple_foots_monster,
        R.drawable.body_quadruple_foots_monster
    ),
    DUAL_WINGS(
        R.string.body_dual_wings,
        R.drawable.body_dual_wings
    ),
    TENTACLE(
        R.string.body_tentacle,
        R.drawable.body_tentacle
    ),
    BUNDLE(
        R.string.body_bundle,
        R.drawable.body_bundle
    ),
    HUMAN(
        R.string.body_human,
        R.drawable.body_human
    ),
    MULTI_WINGS(
        R.string.body_multi_wings,
        R.drawable.body_multi_wings
    ),
    BUG(
        R.string.body_bug,
        R.drawable.body_bug
    ),
    UNKNOWN(
        R.string.body_unknown,
        R.drawable.body_unknown
    )
}