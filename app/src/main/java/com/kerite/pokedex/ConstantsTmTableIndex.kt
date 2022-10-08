package com.kerite.pokedex

import androidx.annotation.IntDef

const val TM_NO = -1
const val TM_GEN_1 = 0
const val TM_GEN_2 = 1
const val TM_GEN_3 = 2
const val TM_GEN_4 = 3
const val TM_GEN_5 = 4
const val TM_GEN_6 = 5
const val TM_ULTRA_SUN_MOON = 6
const val TM_LETS_GO = 7
const val TM_SWORD_SHIELD = 8
const val TM_BRILLIANT_DIAMOND_SHINING_PEARL = 9

@IntDef(
    TM_NO,
    TM_GEN_1,
    TM_GEN_2,
    TM_GEN_3,
    TM_GEN_4,
    TM_GEN_5,
    TM_GEN_6,
    TM_ULTRA_SUN_MOON,
    TM_LETS_GO,
    TM_SWORD_SHIELD,
    TM_BRILLIANT_DIAMOND_SHINING_PEARL
)
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class TmTableIndex {}