package com.kerite.pokedex.model

import com.kerite.pokedex.database.dbview.MoveLearnDatabaseView
import com.kerite.pokedex.database.dbview.MoveTeachDatabaseView
import com.kerite.pokedex.model.enums.EnumGame
import com.kerite.pokedex.model.enums.MoveCategory
import com.kerite.pokedex.model.enums.MovePattern
import com.kerite.pokedex.model.enums.PokemonType

/**
 * 宝可梦学习的技能列表
 * @param name 技能名
 * @param power 威力
 * @param accuracy 命中率
 * @param pp PP值
 * @param type 宝可梦属性
 * @param category 技能类型
 * @param value 等级/技能机
 */
data class PokemonMoveRow(
    val id: Int,
    val name: String,
    val power: PowerAccuracyValue,
    val accuracy: PowerAccuracyValue,
    val pp: PowerAccuracyValue,
    val type: PokemonType,
    val category: MoveCategory,
    val description: String,
    val value: String,
    val pattern: MovePattern
) {
    companion object {
        /**
         * Transfer a MoveLearnView to a row which displayed in move list
         * @return return null if no value
         */
        fun fromMoveLearnView(view: MoveLearnDatabaseView, game: EnumGame): PokemonMoveRow? {
            var shownText = game.moveLearnProperty.get(view)
            if (view.pattern == MovePattern.BREED) {
                shownText = "遗传"
            }
            if (shownText.isNullOrBlank()) {
                return null
            }
            if (shownText.startsWith("—<")) {
                shownText = "进化"
            }
            return PokemonMoveRow(
                id = view.id,
                name = view.name,
                power = when (view.power) {
                    "—" -> PowerAccuracyValue.NoValue
                    "变化" -> PowerAccuracyValue.Variably
                    else -> PowerAccuracyValue.IntValue(view.power.toInt())
                },
                accuracy = when (view.accuracy) {
                    "—" -> PowerAccuracyValue.NoValue
                    "" -> PowerAccuracyValue.Variably
                    else -> PowerAccuracyValue.IntValue(view.accuracy.toInt())
                },
                pp = when (view.pp) {
                    "—" -> PowerAccuracyValue.NoValue
                    else -> PowerAccuracyValue.IntValue(view.pp.toInt())
                },
                type = view.type,
                category = view.damageCategory,
                value = shownText,
                description = view.description,
                pattern = view.pattern
            )
        }

        fun fromMoveTeachView(view: MoveTeachDatabaseView, game: EnumGame): PokemonMoveRow? {
            val canBeTeach = game.moveTeachProperty?.get(view)
            if (canBeTeach != true) {
                return null
            }
            return PokemonMoveRow(
                id = view.id,
                name = view.name,
                power = when (view.power) {
                    "—" -> PowerAccuracyValue.NoValue
                    "变化" -> PowerAccuracyValue.Variably
                    else -> PowerAccuracyValue.IntValue(view.power.toInt())
                },
                accuracy = when (view.accuracy) {
                    "—" -> PowerAccuracyValue.NoValue
                    "" -> PowerAccuracyValue.Variably
                    else -> PowerAccuracyValue.IntValue(view.accuracy.toInt())
                },
                pp = when (view.pp) {
                    "—" -> PowerAccuracyValue.NoValue
                    else -> PowerAccuracyValue.IntValue(view.pp.toInt())
                },
                type = view.type,
                category = view.damageCategory,
                value = "",
                description = view.description,
                pattern = MovePattern.TEACH
            )
        }
    }
}