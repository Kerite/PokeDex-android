package com.kerite.pokedex.model.enums

import androidx.annotation.StringRes
import com.kerite.pokedex.database.dbview.MoveLearnDatabaseView
import kotlin.reflect.KProperty1

/**
 * 游戏列表
 * @param generation 游戏世代
 * @param gameName 游戏名字
 */
enum class EnumGameList(
    val generation: Int,
    val learnProperty: KProperty1<MoveLearnDatabaseView, String?>,
    @StringRes val gameName: Int = 0,
) {
    RED(1, MoveLearnDatabaseView::redGreenBlue),
    GREEN(1, MoveLearnDatabaseView::redGreenBlue),
    BLUE(1, MoveLearnDatabaseView::redGreenBlue),
    YELLOW(1, MoveLearnDatabaseView::yellow),
    GOLD(2, MoveLearnDatabaseView::goldSilver),
    SILVER(2, MoveLearnDatabaseView::goldSilver),
    CRYSTAL(2, MoveLearnDatabaseView::crystal),
    RUBY(3, MoveLearnDatabaseView::rubySapphireEmerald),
    SAPPHIRE(3, MoveLearnDatabaseView::rubySapphireEmerald),
    FIRE_RED(3, MoveLearnDatabaseView::fireRedLeafGreen),
    LEAF_GREEN(3, MoveLearnDatabaseView::fireRedLeafGreen),
    EMERALD(3, MoveLearnDatabaseView::rubySapphireEmerald),
    DIAMOND(4, MoveLearnDatabaseView::diamondPearl),
    PEARL(4, MoveLearnDatabaseView::diamondPearl),
    HEART_GOLD(4, MoveLearnDatabaseView::heartGoldSoulSilver),
    SOUL_SILVER(4, MoveLearnDatabaseView::heartGoldSoulSilver),
    BLACK(5, MoveLearnDatabaseView::blackWhite),
    WHITE(5, MoveLearnDatabaseView::blackWhite),
    BLACK_2(5, MoveLearnDatabaseView::blackWhite2),
    WHITE_2(5, MoveLearnDatabaseView::blackWhite2),
    X(6, MoveLearnDatabaseView::xy),
    Y(6, MoveLearnDatabaseView::xy),
    OMEGA_RUBY(6, MoveLearnDatabaseView::omegaRubyAlphaSapphire),
    ALPHA_SAPPHIRE(6, MoveLearnDatabaseView::omegaRubyAlphaSapphire),
    SUN(7, MoveLearnDatabaseView::sunMoon),
    MOON(7, MoveLearnDatabaseView::sunMoon),
    ULTRA_SUN(7, MoveLearnDatabaseView::ultraSunUltraMoon),
    ULTRA_MOON(7, MoveLearnDatabaseView::ultraSunUltraMoon),
    LETS_GO(7, MoveLearnDatabaseView::letsGo),
    SHIELD(8, MoveLearnDatabaseView::swordShield),
    SWORD(8, MoveLearnDatabaseView::swordShield),
    BRILLIANT_DIAMOND(8, MoveLearnDatabaseView::brilliantDiamondShinningPearl),
    SHINING_PEARL(8, MoveLearnDatabaseView::brilliantDiamondShinningPearl),
    LEGEND_ARCEUS(8, MoveLearnDatabaseView::legendArceus);

    companion object {
        fun fromInt(order: Int): EnumGameList {
            for (value in values()) {
                if (order == value.ordinal) {
                    return value
                }
            }
            throw IndexOutOfBoundsException()
        }
    }
}