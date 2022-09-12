package com.kerite.pokedex.model

import com.kerite.pokedex.model.enums.*

data class PokemonDex(
    val id: Int,
    /**
     * 图鉴编号
     */
    val dexNumber: Int,
    /**
     * 名字
     */
    val name: String,
    /**
     * 第一属性
     */
    val type1: PokemonType,
    /**
     * 第二属性
     */
    val type2: PokemonType?,
    /**
     * 分类
     */
    val category: String,
    /**
     * 第一特性
     */
    val ability1: String,
    /**
     * 第二特性
     */
    val ability2: String?,
    /**
     * 隐藏特性
     */
    val abilityHidden: String?,
    /**
     * 100级时经验值
     */
    val expRequired: Int,
    /**
     * 身高(m)
     */
    val height: Float,
    /**
     * 体重
     */
    val weight: Float,
    /**
     * 体型
     */
    val shape: Shape,
    /**
     * 图鉴颜色
     */
    val color: Color,
    /**
     * 捕获率
     */
    val catchRate: Int,
    /**
     * 性别分布
     */
    val genderRate: Int,
    /**
     * 蛋组
     */
    val eggGroup: Array<EggGroup>,
    /**
     * 孵化周期
     */
    val eggCycle: Int,
    /**
     *  基础点数
     */
    val basePoint: PokemonPoint,
    /**
     * 基础经验值
     */
    val baseExp: Int,
) {}