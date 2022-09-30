package com.kerite.pokedex.database.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.kerite.pokedex.model.enums.MovePattern

data class PokeDexMoveLearnEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "move_id") val moveId: Int,
    @ColumnInfo(name = "dex_number") val dexNumber: Int,
    @ColumnInfo(name = "form_name") val formName: String?,
    @ColumnInfo(name = "pattern") val pattern: MovePattern,
    /**
     * 游戏版本 红绿蓝
     */
    @ColumnInfo(name = "red_green_blue") val redGreenBlue: String?,
    /**
     * 游戏版本 皮卡丘
     */
    @ColumnInfo(name = "yellow") val yellow: String?,
    /**
     * 游戏版本 金银
     */
    @ColumnInfo(name = "gold_silver") val goldSilver: String?,
    @ColumnInfo(name = "crystal") val crystal: String?,
    @ColumnInfo(name = "ruby_sapphire_emerald") val rubySapphireEmerald: String?,
    @ColumnInfo(name = "platinum") val platinum: String?,
    @ColumnInfo(name = "heart_gold_soul_silver") val heartGoldSoulSilver: String?,
    @ColumnInfo(name = "black_white") val blackWhite: String?,
    @ColumnInfo(name = "black_white_2") val blackWhite2: String?,
    @ColumnInfo(name = "x_y") val xy: String?,
    @ColumnInfo(name = "omega_ruby_alpha_sapphire") val omegaRubyAlphaSapphire: String?,
    @ColumnInfo(name = "sun_moon") val sunMoon: String?,
    @ColumnInfo(name = "ultra_sun_ultra_moon") val ultraSunUltraMoon: String?,
    @ColumnInfo(name = "lets_go") val letsGo: String?,
    @ColumnInfo(name = "sword_shield") val swordShield: String?,
    @ColumnInfo(name = "brilliant_diamond_shinning_pearl") val brilliantDiamondShinningPearl: String?,
    @ColumnInfo(name = "legend_arceus") val legendArceus: String?,
    @ColumnInfo(name = "additional_gender") val additionalGender: String?,
    @ColumnInfo(name = "additional_note") val additionalNote: String?,
    @ColumnInfo(name = "additional_generation") val additionalGeneration: String?,
    @ColumnInfo(name = "additional_except") val additionalExcept: String?
)