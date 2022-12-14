@file:Suppress("unused")

package com.kerite.pokedex

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kerite.pokedex.model.enums.EnumGame

// MSP图像每行的精灵数量
val MSP_WIDTH = 30

// MSP图像的行数
val MSP_HEIGHT = 42

// 极巨化的精灵的图鉴序号
val GIGANTAMAX_DEX_NUMBERS = arrayOf(
    3, 6, 9, 12, 25, 52, 68, 94, 99, 131, 133, 143, 569, 809, 812, 815, 818,
    823, 826, 834, 839, 841, 842, 844, 849, 851, 858, 861, 869, 879, 884, 892,
)

// 有MEGA形态的精灵图鉴编号
val MEGA_DEX_NUMBER = arrayOf(
    3, 6, 9, 15, 18, 65, 80, 94, 115, 127, 130, 142, 150, 181, 208, 212, 214, 229, 248, 254, 260,
    257, 282, 302, 303, 306, 308, 310, 319, 323, 334, 354, 359, 362, 373, 376, 380, 381, 384, 428,
    445, 448, 460, 475, 531, 719
)

// 有两种MEGA的精灵图鉴编号
val MEGA_COUNT_2 = arrayOf(
    6, 150
)

// 未知图腾图鉴编号
const val UNOWN_DEX_NUMBER = 201
const val UNOWN_COUNT = 28

const val INTENT_POKEMON_LIST = "pokemon_list"
const val INTENT_TOOL_BOX = "tool_box"

const val COUNT_GENERATION = 8

val STATISTIC_PERCENT_BEFORE_THREE_GENERATION = listOf<Float>(
    0.25f, 0.28f, 0.33f, 0.4f, 0.5f, 0.66f, 1f, 1.5f, 2f, 2.5f, 3f, 3.5f, 4f
)

object SettingsConstants {
    const val SETTINGS_CHECK_UPDATE_STR = "action_check_update"

    /**
     * 低性能模式
     */
    const val SETTINGS_LOW_PERFORMANCE_KEY_STR = "low_performance"
    const val SETTINGS_LOW_PERFORMANCE_DEFAULT = false
    val SETTINGS_LOW_PERFORMANCE_KEY = booleanPreferencesKey(SETTINGS_LOW_PERFORMANCE_KEY_STR)

    /**
     * 收集匿名数据
     */
    const val SETTINGS_ANONYMOUS_ANALYTICS_ENABLED_STR = "preference_anonymous_analyze"
    const val SETTINGS_ANONYMOUS_ANALYTICS_ENABLED_DEFAULT = true
    val SETTINGS_ANONYMOUS_ANALYTICS_ENABLED =
        booleanPreferencesKey(SETTINGS_ANONYMOUS_ANALYTICS_ENABLED_STR)

    /**
     * 自动检查更新
     */
    const val SETTINGS_AUTO_CHECK_UPDATE_STR = "preference_auto_check_update"
    const val SETTINGS_AUTO_CHECK_UPDATE_DEFAULT = true
    val SETTINGS_AUTO_CHECK_UPDATE_KEY = booleanPreferencesKey(SETTINGS_AUTO_CHECK_UPDATE_STR)

    /**
     * 选择的游戏
     */
    const val SETTINGS_GAME_STR = "preference_game"
    val SETTINGS_GAME_DEFAULT = EnumGame.SWORD.name
    val SETTINGS_GAME_KEY = stringPreferencesKey(SETTINGS_GAME_STR)
}