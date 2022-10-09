package com.kerite.pokedex.util

import com.kerite.pokedex.model.enums.PokemonType

object PokemonDamageUtils {

    private val superEffective = mutableMapOf<PokemonType, List<PokemonType>>()
    private val normalEffectiveCache = mutableMapOf<PokemonType, List<PokemonType>>()
    private val weakEffectiveCache = mutableMapOf<PokemonType, List<PokemonType>>()
    private val noEffectCache = mutableMapOf<PokemonType, List<PokemonType>>()


    /**
     * 获取效果绝佳的属性（攻击时）
     * @param type 攻击方属性
     */
    fun getSuperEffectiveTypes(type: PokemonType): List<PokemonType> = superEffective.getOrPut(type) {
        DAMAGE_MULTIPLIER_ATTACK[type.ordinal].mapIndexed { index, fl ->
            index to fl
        }.filter {
            it.second == 2f
        }.map {
            PokemonType.values[it.first]
        }
    }

    /**
     * 获取效果不好的属性（攻击时）
     * @param type 攻击方属性
     */
    fun getWeakEffectiveTypes(type: PokemonType): List<PokemonType> = weakEffectiveCache.getOrPut(type) {
        DAMAGE_MULTIPLIER_ATTACK[type.ordinal].mapIndexed { index, fl ->
            index to fl
        }.filter {
            it.second == 0.5f
        }.map {
            PokemonType.values[it.first]
        }
    }

    /**
     * 获取有效果的属性（攻击时）
     * @param type 攻击方属性
     */
    fun getNormalEffectTypes(type: PokemonType): List<PokemonType> = normalEffectiveCache.getOrPut(type) {
        DAMAGE_MULTIPLIER_ATTACK[type.ordinal].mapIndexed { index, fl ->
            index to fl
        }.filter {
            it.second == 1f
        }.map {
            PokemonType.values[it.first]
        }
    }

    /**
     * 获取 无效的属性（攻击时）
     * @param type 攻击方的属性
     */
    fun getNoEffectTypes(type: PokemonType): List<PokemonType> = noEffectCache.getOrPut(type) {
        DAMAGE_MULTIPLIER_ATTACK[type.ordinal].mapIndexed { index, fl ->
            index to fl
        }.filter {
            it.second == 0f
        }.map {
            PokemonType.values[it.first]
        }
    }

    /**
     * 获取弱点（作为被攻击方）
     * @return
     */
    fun getWeaknessAsDefender(vararg types: PokemonType): List<Pair<PokemonType, Float>> {
        val resultList = mutableListOf<Pair<PokemonType, Float>>()
        PokemonType.values.forEach { attacker ->
            val damageMultiplier = calculateAttackDamageRate(attacker, *types)
            if (damageMultiplier > 1) {
                resultList.add(attacker to damageMultiplier)
            }
        }
        return resultList
    }

    /**
     * 获取普通（作为被攻击方）
     * @return
     */
    fun getNormalAsDefender(vararg types: PokemonType): List<Pair<PokemonType, Float>> {
        val resultList = mutableListOf<Pair<PokemonType, Float>>()
        PokemonType.values.forEach { attacker ->
            val damageMultiplier = calculateAttackDamageRate(attacker, *types)
            if (damageMultiplier == 1f) {
                resultList.add(attacker to damageMultiplier)
            }
        }
        return resultList
    }

    /**
     * 获取有抗性（作为被攻击方）
     * @return
     */
    fun getEffectiveAsDefender(vararg types: PokemonType): List<Pair<PokemonType, Float>> {
        val resultList = mutableListOf<Pair<PokemonType, Float>>()
        PokemonType.values.forEach { attacker ->
            val damageMultiplier = calculateAttackDamageRate(attacker, *types)
            if (damageMultiplier < 1) {
                resultList.add(attacker to damageMultiplier)
            }
        }
        return resultList
    }

    /**
     * 获取属性相性（作为被攻击方）
     * @param types 被攻击的属性
     * @return 每种属性和它的伤害
     */
    fun getTypeDamageMultiplierAsDefenderPairList(vararg types: PokemonType): List<Pair<PokemonType, Float>> {
        return PokemonType.values.map {
            it to calculateAttackDamageRate(it, *types)
        }
    }

    /**
     * 获取属性相性（作为被攻击方）
     * @param types 被攻击的属性
     * @return 每种属性和它的伤害
     */
    fun getTypeDamageMultiplierAsDefenderList(vararg types: PokemonType): List<Float> {
        return PokemonType.values.map {
            calculateAttackDamageRate(it, *types)
        }
    }

    /**
     * 计算伤害倍率
     * @param typeOfAttack 攻击方属性
     * @param typesOfDefence 防御方属性
     */
    fun calculateAttackDamageRate(typeOfAttack: PokemonType, vararg typesOfDefence: PokemonType): Float {
        var currentDamageRate = 1f
        for (type in typesOfDefence) {
            currentDamageRate *= DAMAGE_MULTIPLIER_ATTACK[typeOfAttack.ordinal][type.ordinal]
        }
        return currentDamageRate
    }

    /**
     * 伤害倍率（作为攻击方）
     */
    val DAMAGE_MULTIPLIER_ATTACK by lazy {
        arrayOf(
            //      1×  1×  1×  1×  1×  1⁄2×  1×  0×  1⁄2×  1×  1×  1×  1×  1×  1×  1×  1×  1×
            arrayOf(1f, 1f, 1f, 1f, 1f, 0.5f, 1f, 0f, 0.5f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f), // 一般
            //      2×  1×  1⁄2×  1⁄2×  1×  2×  1⁄2×  0×  2×  1×  1×  1×  1×  1⁄2×  2×  1×  2×  1⁄2×
            arrayOf(2f, 1f, 0.5f, 0.5f, 1f, 2f, 0.5f, 0f, 2f, 1f, 1f, 1f, 1f, 0.5f, 2f, 1f, 2f, 0.5f), // 格斗
            //      1×  2×  1×  1×  1×  1⁄2×  2×  1×  1⁄2×  1×  1×  2×  1⁄2×  1×  1×  1×  1×  1×
            arrayOf(1f, 2f, 1f, 1f, 1f, 0.5f, 2f, 1f, 0.5f, 1f, 1f, 2f, 0.5f, 1f, 1f, 1f, 1f, 1f), // 飞行
            //      1×  1×  1×  1⁄2×  1⁄2×  1⁄2×  1×  1⁄2×  0×  1×  1×  2×  1×  1×  1×  1×  1×  2×
            arrayOf(1f, 1f, 1f, 0.5f, 0.5f, 0.5f, 1f, 0.5f, 0f, 1f, 1f, 2f, 1f, 1f, 1f, 1f, 1f, 2f), //毒
            //      1×  1×  0×  2×  1×  2×  1⁄2×  1×  2×  2×  1×  1⁄2×  2×  1×  1×  1×  1×  1×
            arrayOf(1f, 1f, 0f, 2f, 1f, 2f, 0.5f, 1f, 2f, 2f, 1f, 0.5f, 2f, 1f, 1f, 1f, 1f, 1f), // 地面
            //      1×  1⁄2×  2×  1×  1⁄2×  1×  2×  1×  1⁄2×  2×  1×  1×  1×  1×  2×  1×  1×  1×
            arrayOf(1f, 0.5f, 2f, 1f, 0.5f, 1f, 2f, 1f, 0.5f, 2f, 1f, 1f, 1f, 1f, 2f, 1f, 1f, 1f), // 岩石
            //      1×  1⁄2×  1⁄2×  1⁄2×  1×  1×  1×  1⁄2×  1⁄2×  1⁄2×  1×  2×  1×  2×  1×  1×  2×  1⁄2×
            arrayOf(1f, 0.5f, 0.5f, 0.5f, 1f, 1f, 1f, 0.5f, 0.5f, 0.5f, 1f, 2f, 1f, 2f, 1f, 1f, 2f, 0.5f), // 虫
            //      0×  1×  1×  1×  1×  1×  1×  2×  1×  1×  1×  1×  1×  2×  1×  1×  1⁄2×  1×
            arrayOf(0f, 1f, 1f, 1f, 1f, 1f, 1f, 2f, 1f, 1f, 1f, 1f, 1f, 2f, 1f, 1f, 0.5f, 1f), // 幽灵
            //      1×  1×  1×  1×  1×  2×  1×  1×  1⁄2×  1⁄2×  1⁄2×  1×  1⁄2×  1×  2×  1×  1×  2×
            arrayOf(1f, 1f, 1f, 1f, 1f, 2f, 1f, 1f, 0.5f, 0.5f, 0.5f, 1f, 0.5f, 1f, 2f, 1f, 1f, 2f), // 钢
            //      1×  1×  1×  1×  1×  1⁄2×  2×  1×  2×  1⁄2×  1⁄2×  2×  1×  1×  2×  1⁄2×  1×  1×
            arrayOf(1f, 1f, 1f, 1f, 1f, 0.5f, 2f, 1f, 2f, 0.5f, 0.5f, 2f, 1f, 1f, 2f, 0.5f, 1f, 1f), // 火
            //      1×  1×  1×  1×  2×  2×  1×  1×  1×  2×  1⁄2×  1⁄2×  1×  1×  1×  1⁄2×  1×  1×
            arrayOf(1f, 1f, 1f, 1f, 2f, 2f, 1f, 1f, 1f, 2f, 0.5f, 0.5f, 1f, 1f, 1f, 0.5f, 1f, 1f), // 水
            //      1×  1×  1⁄2×  1⁄2×  2×  2×  1⁄2×  1×  1⁄2×  1⁄2×  2×  1⁄2×  1×  1×  1×  1⁄2×  1×  1×
            arrayOf(1f, 1f, 0.5f, 0.5f, 2f, 2f, 0.5f, 1f, 0.5f, 0.5f, 2f, 0.5f, 1f, 1f, 1f, 0.5f, 1f, 1f), // 草
            //      1×  1×  2×  1×  0×  1×  1×  1×  1×  1×  2×  1⁄2×  1⁄2×  1×  1×  1⁄2×  1×  1×
            arrayOf(1f, 1f, 2f, 1f, 0f, 1f, 1f, 1f, 1f, 1f, 2f, 0.5f, 0.5f, 1f, 1f, 0.5f, 1f, 1f), // 电
            //      1×  2×  1×  2×  1×  1×  1×  1×  1⁄2×  1×  1×  1×  1×  1⁄2×  1×  1×  0×  1×
            arrayOf(1f, 2f, 1f, 2f, 1f, 1f, 1f, 1f, 0.5f, 1f, 1f, 1f, 1f, 0.5f, 1f, 1f, 0f, 1f), // 超能力
            //      1×  1×  2×  1×  2×  1×  1×  1×  1⁄2×  1⁄2×  1⁄2×  2×  1×  1×  1⁄2×  2×  1×  1×
            arrayOf(1f, 1f, 2f, 1f, 2f, 1f, 1f, 1f, 0.5f, 0.5f, 0.5f, 2f, 1f, 1f, 0.5f, 2f, 1f, 1f), // 冰
            //      1×  1×  1×  1×  1×  1×  1×  1×  1⁄2×  1×  1×  1×  1×  1×  1×  2×  1×  0×
            arrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 0.5f, 1f, 1f, 1f, 1f, 1f, 1f, 2f, 1f, 0f), // 龙
            //      1×  1⁄2×  1×  1×  1×  1×  1×  2×  1×  1×  1×  1×  1×  2×  1×  1×  1⁄2×  1⁄2×
            arrayOf(1f, 0.5f, 1f, 1f, 1f, 1f, 1f, 2f, 1f, 1f, 1f, 1f, 1f, 2f, 1f, 1f, 0.5f, 0.5f), // 恶
            //      1×  2×  1×  1⁄2×  1×  1×  1×  1×  1⁄2×  1⁄2×  1×  1×  1×  1×  1×  2×  2×  1×
            arrayOf(1f, 2f, 1f, 0.5f, 1f, 1f, 1f, 1f, 0.5f, 0.5f, 1f, 1f, 1f, 1f, 1f, 2f, 2f, 1f)
        )
    }
}