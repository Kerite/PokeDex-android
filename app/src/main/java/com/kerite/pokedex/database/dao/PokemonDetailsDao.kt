package com.kerite.pokedex.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.kerite.pokedex.database.entity.PokemonDetailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDetailsDao {
    @Query("SELECT * FROM pokemon_detail")
    fun getFlowAll(): Flow<List<PokemonDetailsEntity>>

    @Query("SELECT * FROM pokemon_detail WHERE dex_number = :dexNumber")
    fun findByDexNumber(dexNumber: Int): Flow<List<PokemonDetailsEntity>>

    @Query("SELECT * FROM pokemon_detail WHERE dex_number = :dexNumber")
    fun findFlowByDexNumber(dexNumber: Int): Flow<List<PokemonDetailsEntity>>

    @Query("SELECT * FROM pokemon_detail WHERE ability_1 = :abilityName OR ability_2 = :abilityName OR ability_hidden = :abilityName")
    fun filterByAbility(abilityName: String): List<PokemonDetailsEntity>
}