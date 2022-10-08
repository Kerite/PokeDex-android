package com.kerite.pokedex.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kerite.pokedex.TmTableIndex

@Entity(tableName = "pokemon_tm")
data class PokeDexTmEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "tm_number") val tmNumber: Int,
    @ColumnInfo(name = "move_id") val moveId: Int,
    @TmTableIndex @ColumnInfo(name = "table_index") val tableIndex: Int
)