package com.kerite.pokedex.database.entity

import androidx.core.net.toUri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.kerite.pokedex.model.enums.EggGroup
import com.kerite.pokedex.model.enums.PokemonBody
import com.kerite.pokedex.model.enums.PokemonType

/**
 * 宝可梦详情
 * @param id 宝可梦ID
 * @param dexNumber 图鉴编号
 * @param name 宝可梦名字
 * @param jpName 日文名字
 * @param enName 英文名字
 * @param formName 形态名字
 * @param height 身高
 * @param weight 体重
 * @param type1 属性1
 * @param type2 属性2
 * @param body 体形
 * @param catchRate 捕获率
 * @param specie 分类
 * @param ability1 特性1
 * @param ability2 特性2
 * @param abilityHidden 隐藏特性
 * @param evHp 击倒后生命努力值
 * @param evAttack 击倒后攻击努力值
 */
@Entity(
    tableName = "pokemon_detail"
)
data class PokemonDetailsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "dex_number") val dexNumber: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "jp_name") val jpName: String,
    @ColumnInfo(name = "en_name") val enName: String,
    @ColumnInfo(name = "form_name") val formName: String?,
    @ColumnInfo(name = "height") val height: String,
    @ColumnInfo(name = "weight") val weight: String,
    @ColumnInfo(name = "type_1") val type1: PokemonType,
    @ColumnInfo(name = "type_2") val type2: PokemonType?,
    @ColumnInfo(name = "body") val body: PokemonBody,
    @ColumnInfo(name = "catch_rate") val catchRate: Int,
    @ColumnInfo(name = "specie") val specie: String,
    @ColumnInfo(name = "ability_1") val ability1: String,
    @ColumnInfo(name = "ability_2") val ability2: String?,
    @ColumnInfo(name = "ability_hidden") val abilityHidden: String?,
    @ColumnInfo(name = "ev_hp") val evHp: Int,
    @ColumnInfo(name = "ev_attack") val evAttack: Int,
    @ColumnInfo(name = "ev_defence") val evDefence: Int,
    @ColumnInfo(name = "ev_special_attack") val evSpecialAttack: Int,
    @ColumnInfo(name = "ev_special_defence") val evSpecialDefence: Int,
    @ColumnInfo(name = "ev_speed") val evSpeed: Int,
    @ColumnInfo(name = "exp_yield") val expYield: Int?,
    @ColumnInfo(name = "exp_yield_gen_5") val expYieldGen5: Int?,
    @ColumnInfo(name = "exp_lv_100") val expLv100: Int,
    @ColumnInfo(name = "egg_group_1") val eggGroup1: EggGroup,
    @ColumnInfo(name = "egg_group_2") val eggGroup2: EggGroup?,
    @ColumnInfo(name = "egg_cycle") val eggCycle: Int,
    @ColumnInfo(name = "hp") val hp: Int,
    @ColumnInfo(name = "attack") val attack: Int,
    @ColumnInfo(name = "defence") val defence: Int,
    @ColumnInfo(name = "special_attack") val specialAttack: Int,
    @ColumnInfo(name = "special_defence") val specialDefence: Int,
    @ColumnInfo(name = "speed") val speed: Int
) {
    @get:Ignore
    @delegate:Ignore
    val imageUri by lazy {
        "file:///android_asset/images/${dexNumber}_${name}_${formName ?: ""}_.webp"
            .replace("__", "_")
            .toUri()
    }
}