package com.kerite.pokedex.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kerite.pokedex.model.enums.PokemonRegionalVariant
import com.kerite.pokedex.model.enums.PokemonType

@Entity(
    indices = [Index(
        value = [
            "dex_number", "type_1", "type_2"
        ]
    )],
    tableName = "pokemon_summary"
)
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "dex_number") val dexNumber: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "sub_name") val subName: PokemonRegionalVariant,
    @ColumnInfo(name = "type_1") val type1: PokemonType,
    @ColumnInfo(name = "type_2") val type2: PokemonType? = null,
    @ColumnInfo(name = "generation") val generation: Int,
    @ColumnInfo(name = "icon_row_index") val iconRowIndex: Int,
    @ColumnInfo(name = "icon_column_index") val iconColumnIndex: Int
) {}