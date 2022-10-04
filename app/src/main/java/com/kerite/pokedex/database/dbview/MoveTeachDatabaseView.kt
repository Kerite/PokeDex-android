package com.kerite.pokedex.database.dbview

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import com.kerite.pokedex.model.enums.MoveCategory
import com.kerite.pokedex.model.enums.MovePattern
import com.kerite.pokedex.model.enums.PokemonType

@DatabaseView(
    "SELECT pokemon_move.id, " +
            "pokemon_move_teach.dex_number AS dexNumber, " +
            "pokemon_move_teach.form_name AS formName, " +
            "'TEACH' AS pattern," +
            "pokemon_move.name, pokemon_move.type, " +
            "pokemon_move.damage_category AS damageCategory, " +
            "pokemon_move.pp, pokemon_move.power, pokemon_move.accuracy, " +
            "pokemon_move.generation, pokemon_move.description, " +
            "pokemon_move_teach.crystal, " +
            "pokemon_move_teach.emerald, pokemon_move_teach.fire_red_leaf_green, " +
            "pokemon_move_teach.diamond_pearl, pokemon_move_teach.platinum, " +
            "pokemon_move_teach.heart_gold_soul_silver, pokemon_move_teach.black_white, " +
            "pokemon_move_teach.black_white_2, pokemon_move_teach.x_y, " +
            "pokemon_move_teach.omega_ruby_alpha_sapphire, pokemon_move_teach.sun_moon, " +
            "pokemon_move_teach.ultra_sun_ultra_moon, pokemon_move_teach.lets_go, " +
            "pokemon_move_teach.sword_shield, pokemon_move_teach.brilliant_diamond_shining_pearl," +
            "pokemon_move_teach.legends_arceus " +
            "FROM pokemon_move LEFT JOIN pokemon_move_teach ON " +
            "pokemon_move_teach.move_id = pokemon_move.id"
)
data class MoveTeachDatabaseView(
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
    @ColumnInfo(name = "crystal") val crystal: Boolean,
    @ColumnInfo(name = "emerald") val emerald: Boolean,
    @ColumnInfo(name = "fire_red_leaf_green") val fireRedLeafGreen: Boolean,
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