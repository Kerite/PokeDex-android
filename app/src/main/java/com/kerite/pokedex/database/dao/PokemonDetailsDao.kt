package com.kerite.pokedex.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.kerite.pokedex.database.entity.PokemonDetailsEntity
import com.kerite.pokedex.model.enums.EggGroup
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

    @Query("SELECT * FROM pokemon_detail WHERE egg_group_1 = :eggGroup OR egg_group_2 = :eggGroup")
    fun filterByEggGroup(eggGroup: EggGroup): List<PokemonDetailsEntity>
}