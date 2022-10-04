package com.kerite.pokedex.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pokemon_move_teach",
    indices = [
        Index(name = "idx__pokemon_move_teach__pokemon_id", unique = false, value = ["dex_number"]),
        Index(name = "idx__pokemon_move_teach__move_id", unique = false, value = ["move_id"])
    ]
)
data class PokeDexMoveTeachEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "move_id") val moveId: Int,
    @ColumnInfo(name = "dex_number") val dexNumber: Int,
    @ColumnInfo(name = "form_name") val formName: String?,
    @ColumnInfo(name = "crystal") val crystal: Boolean,
    @ColumnInfo(name = "fire_red_leaf_green") val fireRedLeafGreen: Boolean,
    @ColumnInfo(name = "emerald") val emerald: Boolean,
    @ColumnInfo(name = "diamond_pearl") val diamondPearl: Boolean,
    @ColumnInfo(name = "platinum") val platinum: Boolean,
    @ColumnInfo(name = "heart_gold_soul_silver") val heartGoldSoulSilver: Boolean,
    @ColumnInfo(name = "black_white") val blackWhite: Boolean,
    @ColumnInfo(name = "black_white_2") val blackWhite2: Boolean,
    @ColumnInfo(name = "x_y") val xy: Boolean,
    @ColumnInfo(name = "omega_ruby_alpha_sapphire") val omegaRubyAlphaSapphire: Boolean,
    @ColumnInfo(name = "sun_moon") val sunMoon: Boolean,
    @ColumnInfo(name = "ultra_sun_ultra_moon") val ultraSunUltraMoon: Boolean,
    @ColumnInfo(name = "lets_go") val letsGo: Boolean,
    @ColumnInfo(name = "sword_shield") val swordShield: Boolean,
    @ColumnInfo(name = "brilliant_diamond_shining_pearl") val brilliantDiamondShiningPearl: Boolean,
    @ColumnInfo(name = "legends_arceus") val legendsArceus: Boolean,
)