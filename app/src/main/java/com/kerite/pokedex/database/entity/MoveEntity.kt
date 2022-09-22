package com.kerite.pokedex.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kerite.pokedex.model.enums.MoveType
import com.kerite.pokedex.model.enums.PokemonType

@Entity(
    tableName = "move_summary"
)
data class MoveEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "jp_name") val jpName: String,
    @ColumnInfo(name = "eng_name") val engName: String,
    @ColumnInfo(name = "type") val type: PokemonType,
    @ColumnInfo(name = "move_type") val moveType: MoveType,
    @ColumnInfo(name = "damage") val damage: Int,
    @ColumnInfo(name = "accuracy") val accuracy: Int,
    @ColumnInfo(name = "pp") val pp: Int,
    @ColumnInfo(name = "description") val description: String
) {}