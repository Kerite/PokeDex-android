package com.kerite.pokedex.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kerite.pokedex.entity.PokemonEntity
import com.kerite.pokedex.model.PokemonSearchFilter
import com.kerite.pokedex.model.enums.PokemonRegionalVariant
import com.kerite.pokedex.model.enums.PokemonType

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon_summary")
    fun getAll(): List<PokemonEntity>

    @Query("SELECT * FROM pokemon_summary")
    fun getAllLiveData(): LiveData<List<PokemonEntity>>

    @Query(
        "SELECT * FROM pokemon_summary WHERE " +
                "name LIKE '%' | :name | '%' AND " +
                "(type_1 in (:types) OR type_2 in (:types)) AND " +
                "sub_name in (:subNames)"
    )
    fun filterLiveDataByFilter(
        name: String,
        types: Array<PokemonType>,
        subNames: Array<PokemonRegionalVariant>
    ): LiveData<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon_summary WHERE name LIKE :name")
    fun filterLiveDataByName(name: String): LiveData<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon_summary WHERE dex_number in (:dexNumber)")
    fun loadAllByDexNumber(dexNumber: IntArray): List<PokemonEntity>

    @Query("SELECT * FROM pokemon_summary WHERE dex_number = :dexNumber AND sub_name = ''")
    fun findByDexNumber(dexNumber: Int): PokemonEntity

    @Query("SELECT * FROM pokemon_summary WHERE name LIKE :name")
    fun loadAllByName(name: String): List<PokemonEntity>

    @Insert
    fun insertAll(vararg pokemons: PokemonEntity)

    @Delete
    fun delete(pokemons: PokemonEntity)
}