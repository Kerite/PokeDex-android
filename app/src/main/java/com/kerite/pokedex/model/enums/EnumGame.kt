package com.kerite.pokedex.model.enums

import androidx.annotation.StringRes
import com.kerite.pokedex.R
import com.kerite.pokedex.TM_BRILLIANT_DIAMOND_SHINING_PEARL
import com.kerite.pokedex.TM_GEN_1
import com.kerite.pokedex.TM_GEN_2
import com.kerite.pokedex.TM_GEN_3
import com.kerite.pokedex.TM_GEN_4
import com.kerite.pokedex.TM_GEN_5
import com.kerite.pokedex.TM_GEN_6
import com.kerite.pokedex.TM_LETS_GO
import com.kerite.pokedex.TM_NO
import com.kerite.pokedex.TM_SWORD_SHIELD
import com.kerite.pokedex.TM_ULTRA_SUN_MOON
import com.kerite.pokedex.TmTableIndex
import com.kerite.pokedex.database.dbview.MoveLearnDatabaseView
import com.kerite.pokedex.database.dbview.MoveTeachDatabaseView
import kotlin.reflect.KProperty1

/**
 * 游戏列表
 * @param generation 游戏世代
 * @param gameName 游戏名字
 * @param tmTableIndex 技能机数据表中对应的 table_index 列的值，-1表示该作没有技能机
 */
enum class EnumGame(
    val generation: Int,
    val moveLearnProperty: KProperty1<MoveLearnDatabaseView, String?>,
    @StringRes val gameName: Int = 0,
    val moveTeachProperty: KProperty1<MoveTeachDatabaseView, Boolean>? = null,
    @TmTableIndex val tmTableIndex: Int = TM_NO,
) {
    RED(
        generation = 1,
        moveLearnProperty = MoveLearnDatabaseView::redGreenBlue,
        gameName = R.string.game_red,
        tmTableIndex = TM_GEN_1
    ),
    GREEN(
        generation = 1,
        moveLearnProperty = MoveLearnDatabaseView::redGreenBlue,
        gameName = R.string.game_green,
        tmTableIndex = TM_GEN_1
    ),
    BLUE(
        generation = 1,
        moveLearnProperty = MoveLearnDatabaseView::redGreenBlue,
        gameName = R.string.game_blue,
        tmTableIndex = TM_GEN_1
    ),
    YELLOW(
        generation = 1,
        moveLearnProperty = MoveLearnDatabaseView::yellow,
        gameName = R.string.game_yellow,
        tmTableIndex = TM_GEN_1
    ),
    GOLD(
        generation = 2,
        moveLearnProperty = MoveLearnDatabaseView::goldSilver,
        gameName = R.string.game_gold,
        tmTableIndex = TM_GEN_2
    ),
    SILVER(
        generation = 2,
        moveLearnProperty = MoveLearnDatabaseView::goldSilver,
        gameName = R.string.game_silver,
        tmTableIndex = TM_GEN_2
    ),
    CRYSTAL(
        generation = 2,
        moveLearnProperty = MoveLearnDatabaseView::crystal,
        gameName = R.string.game_crystal,
        MoveTeachDatabaseView::crystal,
        tmTableIndex = TM_GEN_2
    ),
    RUBY(
        generation = 3,
        moveLearnProperty = MoveLearnDatabaseView::rubySapphireEmerald,
        gameName = R.string.game_ruby,
        tmTableIndex = TM_GEN_3
    ),
    SAPPHIRE(
        generation = 3,
        moveLearnProperty = MoveLearnDatabaseView::rubySapphireEmerald,
        gameName = R.string.game_sapphire,
        tmTableIndex = TM_GEN_3
    ),
    FIRE_RED(
        generation = 3,
        moveLearnProperty = MoveLearnDatabaseView::fireRedLeafGreen,
        gameName = R.string.game_fire_red,
        MoveTeachDatabaseView::fireRedLeafGreen,
        tmTableIndex = TM_GEN_3
    ),
    LEAF_GREEN(
        generation = 3,
        moveLearnProperty = MoveLearnDatabaseView::fireRedLeafGreen,
        gameName = R.string.game_leaf_green,
        MoveTeachDatabaseView::fireRedLeafGreen,
        tmTableIndex = TM_GEN_3
    ),
    EMERALD(
        generation = 3,
        moveLearnProperty = MoveLearnDatabaseView::rubySapphireEmerald,
        gameName = R.string.game_emerald,
        MoveTeachDatabaseView::emerald,
        tmTableIndex = TM_GEN_3
    ),
    DIAMOND(
        generation = 4,
        moveLearnProperty = MoveLearnDatabaseView::diamondPearl,
        gameName = R.string.game_diamond,
        MoveTeachDatabaseView::diamondPearl,
        tmTableIndex = TM_GEN_4
    ),
    PEARL(
        generation = 4,
        moveLearnProperty = MoveLearnDatabaseView::diamondPearl,
        gameName = R.string.game_pearl,
        MoveTeachDatabaseView::diamondPearl,
        tmTableIndex = TM_GEN_4
    ),
    HEART_GOLD(
        generation = 4,
        moveLearnProperty = MoveLearnDatabaseView::heartGoldSoulSilver,
        gameName = R.string.game_heart_gold,
        MoveTeachDatabaseView::heartGoldSoulSilver,
        tmTableIndex = TM_GEN_4
    ),
    SOUL_SILVER(
        generation = 4,
        moveLearnProperty = MoveLearnDatabaseView::heartGoldSoulSilver,
        gameName = R.string.game_soul_silver,
        MoveTeachDatabaseView::heartGoldSoulSilver,
        tmTableIndex = TM_GEN_4
    ),
    BLACK(
        generation = 5,
        moveLearnProperty = MoveLearnDatabaseView::blackWhite,
        gameName = R.string.game_black,
        MoveTeachDatabaseView::blackWhite,
        tmTableIndex = TM_GEN_5
    ),
    WHITE(
        generation = 5,
        moveLearnProperty = MoveLearnDatabaseView::blackWhite,
        gameName = R.string.game_white,
        MoveTeachDatabaseView::blackWhite,
        tmTableIndex = TM_GEN_5
    ),
    BLACK_2(
        generation = 5,
        moveLearnProperty = MoveLearnDatabaseView::blackWhite2,
        gameName = R.string.game_black_2,
        MoveTeachDatabaseView::blackWhite2,
        tmTableIndex = TM_GEN_5
    ),
    WHITE_2(
        generation = 5,
        moveLearnProperty = MoveLearnDatabaseView::blackWhite2,
        gameName = R.string.game_white_2,
        MoveTeachDatabaseView::blackWhite2,
        tmTableIndex = TM_GEN_5
    ),
    X(
        generation = 6,
        moveLearnProperty = MoveLearnDatabaseView::xy,
        gameName = R.string.game_x,
        MoveTeachDatabaseView::xy,
        tmTableIndex = TM_GEN_6
    ),
    Y(
        generation = 6,
        moveLearnProperty = MoveLearnDatabaseView::xy,
        gameName = R.string.game_y,
        MoveTeachDatabaseView::xy,
        tmTableIndex = TM_GEN_6
    ),
    OMEGA_RUBY(
        generation = 6,
        moveLearnProperty = MoveLearnDatabaseView::omegaRubyAlphaSapphire,
        gameName = R.string.game_omega_ruby,
        MoveTeachDatabaseView::omegaRubyAlphaSapphire,
        tmTableIndex = TM_GEN_6
    ),
    ALPHA_SAPPHIRE(
        generation = 6,
        moveLearnProperty = MoveLearnDatabaseView::omegaRubyAlphaSapphire,
        gameName = R.string.game_alpha_sapphire,
        MoveTeachDatabaseView::omegaRubyAlphaSapphire,
        tmTableIndex = TM_GEN_6
    ),
    SUN(
        generation = 7,
        moveLearnProperty = MoveLearnDatabaseView::sunMoon,
        gameName = R.string.game_sun,
        MoveTeachDatabaseView::sunMoon,
        tmTableIndex = TM_ULTRA_SUN_MOON
    ),
    MOON(
        generation = 7,
        moveLearnProperty = MoveLearnDatabaseView::sunMoon,
        gameName = R.string.game_moon,
        MoveTeachDatabaseView::sunMoon,
        tmTableIndex = TM_ULTRA_SUN_MOON
    ),
    ULTRA_SUN(
        generation = 7,
        moveLearnProperty = MoveLearnDatabaseView::ultraSunUltraMoon,
        gameName = R.string.game_ultra_sun,
        MoveTeachDatabaseView::ultraSunUltraMoon,
        tmTableIndex = TM_ULTRA_SUN_MOON
    ),
    ULTRA_MOON(
        generation = 7,
        moveLearnProperty = MoveLearnDatabaseView::ultraSunUltraMoon,
        gameName = R.string.game_ultra_moon,
        MoveTeachDatabaseView::ultraSunUltraMoon,
        tmTableIndex = TM_ULTRA_SUN_MOON
    ),
    LETS_GO_PIKACHU(
        generation = 7,
        moveLearnProperty = MoveLearnDatabaseView::letsGo,
        gameName = R.string.game_lets_go_pikachu,
        MoveTeachDatabaseView::letsGo,
        tmTableIndex = TM_LETS_GO
    ),
    LETS_GO_EEVEE(
        generation = 7,
        moveLearnProperty = MoveLearnDatabaseView::letsGo,
        gameName = R.string.game_lets_go_eevee,
        MoveTeachDatabaseView::letsGo,
        tmTableIndex = TM_LETS_GO
    ),
    SWORD(
        generation = 8,
        moveLearnProperty = MoveLearnDatabaseView::swordShield,
        gameName = R.string.game_sword,
        MoveTeachDatabaseView::swordShield,
        tmTableIndex = TM_SWORD_SHIELD
    ),
    SHIELD(
        generation = 8,
        moveLearnProperty = MoveLearnDatabaseView::swordShield,
        gameName = R.string.game_shield,
        MoveTeachDatabaseView::swordShield,
        tmTableIndex = TM_SWORD_SHIELD
    ),
    BRILLIANT_DIAMOND(
        generation = 8,
        moveLearnProperty = MoveLearnDatabaseView::brilliantDiamondShiningPearl,
        gameName = R.string.game_brilliant_diamond,
        MoveTeachDatabaseView::brilliantDiamondShiningPearl,
        tmTableIndex = TM_BRILLIANT_DIAMOND_SHINING_PEARL
    ),
    SHINING_PEARL(
        generation = 8,
        moveLearnProperty = MoveLearnDatabaseView::brilliantDiamondShiningPearl,
        gameName = R.string.game_shining_pearl,
        MoveTeachDatabaseView::brilliantDiamondShiningPearl,
        tmTableIndex = TM_BRILLIANT_DIAMOND_SHINING_PEARL
    ),
    LEGEND_ARCEUS(
        generation = 8,
        moveLearnProperty = MoveLearnDatabaseView::legendArceus,
        gameName = R.string.game_legends_arceus,
        MoveTeachDatabaseView::legendsArceus
    );
}