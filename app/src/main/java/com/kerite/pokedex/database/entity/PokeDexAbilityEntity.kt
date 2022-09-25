package com.kerite.pokedex.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "pokemon_ability",
)
data class PokeDexAbilityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "jp_name") val jpName: String,
    @ColumnInfo(name = "en_name") val enName: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "generation") val generation: Int
) {}