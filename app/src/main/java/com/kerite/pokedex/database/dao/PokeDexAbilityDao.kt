package com.kerite.pokedex.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.kerite.pokedex.database.entity.PokeDexAbilityEntity

@Dao
interface PokeDexAbilityDao {
    @Query("SELECT * FROM pokemon_ability WHERE name = :abilityName LIMIT 1")
    fun findByAbilityName(abilityName: String): PokeDexAbilityEntity?
}