package com.kerite.pokedex.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kerite.pokedex.model.enums.MoveCategory
import com.kerite.pokedex.model.enums.PokemonType

/**
 * 宝可梦技能列表
 * @param id 表主键
 * @param moveId 技能ID
 * @param name 技能名
 * @param jpName 日文名
 * @param enName 英文名
 * @param type 技能属性
 * @param category 技能分类（物理，特殊，属性）
 * @param power 技能威力
 * @param accuracy 命中率
 * @param pp pp值
 * @param description 技能描述
 * @param generation 技能加入的世代
 * @author Kerite
 */
@Entity(
    tableName = "pokemon_move",
    indices = [
        androidx.room.Index(name = "idx__move_basic__name", unique = false, value = ["name"]),
        androidx.room.Index(
            name = "idx__move_basic__move_id",
            unique = false,
            value = ["move_id"]
        ),
        androidx.room.Index(
            name = "idx__move_basic__move_type",
            unique = false,
            value = ["type"]
        ),
        androidx.room.Index(
            name = "idx__move_basic__damage_category",
            unique = false,
            value = ["damage_category"]
        )
    ]
)
data class PokeDexMoveEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "move_id") val moveId: Int?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "jp_name") val jpName: String,
    @ColumnInfo(name = "en_name") val enName: String,
    @ColumnInfo(name = "type") val type: PokemonType,
    @ColumnInfo(name = "damage_category") val damageCategory: MoveCategory,
    @ColumnInfo(name = "pp") val basePowerPoint: String,
    @ColumnInfo(name = "power") val power: String,
    @ColumnInfo(name = "accuracy") val accuracy: String,
    @ColumnInfo(name = "generation") val generation: Int,
    @ColumnInfo(name = "touches") val touches: Boolean,
    @ColumnInfo(name = "protect") val protect: Boolean,
    @ColumnInfo(name = "magic_coat") val magicCoat: Boolean,
    @ColumnInfo(name = "snatch") val snatch: Boolean,
    @ColumnInfo(name = "mirror_move") val mirrorMove: Boolean,
    @ColumnInfo(name = "kings_rock") val kingsRock: Boolean,
    @ColumnInfo(name = "sound") val sound: Boolean,
    @ColumnInfo(name = "target") val target: Int,
    @ColumnInfo(name = "description") val description: String
)