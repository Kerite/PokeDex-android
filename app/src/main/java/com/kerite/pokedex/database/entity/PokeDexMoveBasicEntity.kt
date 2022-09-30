package com.kerite.pokedex.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kerite.pokedex.model.enums.MoveCategory
import com.kerite.pokedex.model.enums.PokemonType

@Entity(
    tableName = "pokemon_move_basic",
    indices = [
        Index(name = "idx__move_basic__name", unique = false, value = ["name"]),
        Index(name = "idx__move_basic__move_id", unique = false, value = ["move_id"]),
        Index(name = "idx__move_basic__move_type", unique = false, value = ["type"]),
        Index(name = "idx__move_basic__damage_category", unique = false, value = ["damage_category"])
    ]
)
data class PokeDexMoveBasicEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "move_id") val moveId: Int?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "jp_name") val jpName: String,
    @ColumnInfo(name = "en_name") val enName: String,
    @ColumnInfo(name = "type") val type: PokemonType,
    @ColumnInfo(name = "damage_category") val damageCategory: MoveCategory,
    @ColumnInfo(name = "pp") val basePowerPoint: Int,
    @ColumnInfo(name = "power") val power: String,
    @ColumnInfo(name = "accuracy") val accuracy: String,
    @ColumnInfo(name = "generation") val generation: Int,
    /**
     * 是否为接触类招式
     */
    @ColumnInfo(name = "touches") val touch: Boolean,
    /**
     * 是否可以被守住
     */
    @ColumnInfo(name = "protect") val protect: Boolean,
    /**
     * 是否可以被魔法反射
     */
    @ColumnInfo(name = "magic_coat") val magicCoat: Boolean,
    /**
     * 是否可以被抢夺
     */
    @ColumnInfo(name = "snatch") val snatch: Boolean,
    /**
     * 是否可以被鹦鹉学舌
     */
    @ColumnInfo(name = "mirror_move") val mirrorMove: Boolean,
    /**
     * 是否受王者之证影响
     */
    @ColumnInfo(name = "kings_rock") val kingsRock: Boolean,
    /**
     * 是否是声音类招式
     */
    @ColumnInfo(name = "sound") val sound: Boolean,
    /**
     * 目标类型
     */
    @ColumnInfo(name = "target") val target: Int,
)