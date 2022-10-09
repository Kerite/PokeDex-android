package com.kerite.pokedex.util

import com.kerite.pokedex.STATISTIC_PERCENT_BEFORE_THREE_GENERATION
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sqrt

/**
 * 宝可梦相关工具
 */
object PokemonUtils {
    /**
     * 计算属性值
     * @param speciesStrength 种族值
     * @param individualValue Individual Values 个体值
     * @param basePoint basePoint 基础点数
     */
    fun calculateStatistic(
        speciesStrength: Int,
        individualValue: Int,
        basePoint: Int,
        level: Int = 50,
        isHp: Boolean = false,
        isBeforeThirdGeneration: Boolean = false
    ): Int {
        if (isBeforeThirdGeneration) {
            // 第三世代前
            return if (isHp) {
                (speciesStrength + individualValue + (sqrt(basePoint.toFloat()) / 8).roundToInt()) * level / 50 + 10 + level
            } else {
                (speciesStrength + individualValue + (sqrt(basePoint.toFloat()) / 8).roundToInt()) * level / 50 + 5
            }
        } else {
            // 第三世代后
            if (isHp) {
                if (speciesStrength == 1) {
                    return 1
                }
                return (speciesStrength * 2 + individualValue + basePoint / 4) * level / 100 + 10 + level
            } else {
                return (speciesStrength * 2 + individualValue + basePoint / 4) * level / 100 + 5
            }
        }
    }

    /**
     * 计算战斗能力值补正
     * @param valueBefore 计算之前的能力
     * @param level 能力变化等级
     * @param isBeforeThreeGeneration 为第三世代前
     */
    fun calculateStatisticModifiers(
        valueBefore: Int,
        level: Int,
        isBeforeThreeGeneration: Boolean = false
    ): Int {
        if (level >= -6 && level <= 6) {
            throw IllegalArgumentException("Level Must Between -6 And 6")
        }
        return if (isBeforeThreeGeneration) {
            if (level < 0) {
                level * 2 / (2 + abs(level))
            } else {
                level * (2 + level) / 2
            }
        } else {
            (valueBefore * (STATISTIC_PERCENT_BEFORE_THREE_GENERATION[level + 6])).toInt()
        }
    }
}