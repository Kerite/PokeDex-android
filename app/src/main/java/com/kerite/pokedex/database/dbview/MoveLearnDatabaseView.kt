package com.kerite.pokedex.database.dbview

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import com.kerite.pokedex.model.enums.MoveCategory
import com.kerite.pokedex.model.enums.MovePattern
import com.kerite.pokedex.model.enums.PokemonType

@DatabaseView(
    "SELECT pokemon_move.id, " +
            "pokemon_move_learn.dex_number AS dexNumber, " +
            "pokemon_move_learn.form_name AS formName, " +
            "pokemon_move_learn.pattern," +
            "pokemon_move.name, pokemon_move.type, " +
            "pokemon_move.damage_category AS damageCategory, " +
            "pokemon_move.pp, pokemon_move.power, pokemon_move.accuracy, " +
            "pokemon_move.generation, pokemon_move.description, " +
            "pokemon_move_learn.red_green_blue, pokemon_move_learn.yellow, " +
            "pokemon_move_learn.gold_silver, pokemon_move_learn.crystal, " +
            "pokemon_move_learn.ruby_sapphire_emerald, pokemon_move_learn.fire_red_leaf_green, " +
            "pokemon_move_learn.diamond_pearl, pokemon_move_learn.platinum, " +
            "pokemon_move_learn.heart_gold_soul_silver, pokemon_move_learn.black_white, " +
            "pokemon_move_learn.black_white_2, pokemon_move_learn.x_y, " +
            "pokemon_move_learn.omega_ruby_alpha_sapphire, pokemon_move_learn.sun_moon, " +
            "pokemon_move_learn.ultra_sun_ultra_moon, pokemon_move_learn.lets_go, " +
            "pokemon_move_learn.sword_shield, pokemon_move_learn.brilliant_diamond_shinning_pearl," +
            "pokemon_move_learn.legend_arceus " +
            "FROM pokemon_move LEFT JOIN pokemon_move_learn ON " +
            "pokemon_move_learn.move_id = pokemon_move.id"
)
data class MoveLearnDatabaseView(
    val id: Int,
    val dexNumber: Int,
    val formName: String?,
    val pattern: MovePattern,
    val name: String,
    val type: PokemonType,
    val damageCategory: MoveCategory,
    val pp: String,
    val power: String,
    val accuracy: String,
    val generation: Int,
    val description: String,
    @ColumnInfo(name = "red_green_blue") val redGreenBlue: String?,
    @ColumnInfo(name = "yellow") val yellow: String?,
    @ColumnInfo(name = "gold_silver") val goldSilver: String?,
    @ColumnInfo(name = "crystal") val crystal: String?,
    @ColumnInfo(name = "ruby_sapphire_emerald") val rubySapphireEmerald: String?,
    @ColumnInfo(name = "fire_red_leaf_green") val fireRedLeafGreen: String?,
    @ColumnInfo(name = "diamond_pearl") val diamondPearl: String?,
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
)