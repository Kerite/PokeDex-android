package com.kerite.pokedex.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverters
import com.kerite.pokedex.converters.PokemonRegionalVariantConverter
import com.kerite.pokedex.entity.PokemonEntity
import com.kerite.pokedex.model.PokemonSearchFilter
import com.kerite.pokedex.model.enums.PokemonRegionalVariant
import com.kerite.pokedex.model.enums.PokemonType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface PokemonDao {
    @Query(
        "SELECT * FROM pokemon_summary WHERE " +
                "name LIKE '%' || :name || '%' AND " +
                "(type_1 in (:types) OR type_2 in (:types)) AND " +
                "sub_name in (:subNames) AND " +
                "generation in (:generation)"
    )
    fun filterFlowByNameAndFilter(
        name: String,
        types: Set<PokemonType>,
        subNames: Set<PokemonRegionalVariant>,
        generation: Set<Int>
    ): Flow<List<PokemonEntity>>

    @Query(
        "SELECT * FROM pokemon_summary WHERE " +
                "(type_1 in (:types) OR type_2 in (:types)) AND " +
                "sub_name in (:subNames) AND " +
                "generation in (:generation)"
    )
    fun filterFlowByFilter(
        types: Set<PokemonType>,
        subNames: Set<PokemonRegionalVariant>,
        generation: Set<Int>
    ): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon_summary WHERE name LIKE '%' || :name || '%'")
    fun filterFlowByName(name: String): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon_summary")
    fun getAllFlow(): Flow<List<PokemonEntity>>

    @Query(
        "SELECT * FROM pokemon_summary WHERE " +
                "name LIKE '%' || :name || '%' AND " +
                "(type_1 in (:types) OR type_2 in (:types)) AND " +
                "sub_name in (:subNames) AND " +
                "generation in (:generation)"
    )
    fun filterByNameAndFilter(
        name: String,
        types: Set<PokemonType>,
        subNames: Set<PokemonRegionalVariant>,
        generation: Set<Int>
    ): List<PokemonEntity>

    @Query(
        "SELECT * FROM pokemon_summary WHERE " +
                "(type_1 in (:types) OR type_2 in (:types)) AND " +
                "sub_name in (:subNames) AND " +
                "generation in (:generation)"
    )
    fun filterByFilter(
        types: LinkedHashSet<PokemonType>,
        subNames: LinkedHashSet<PokemonRegionalVariant>,
        generation: LinkedHashSet<Int>
    ): List<PokemonEntity>

    @Query("SELECT * FROM pokemon_summary WHERE name LIKE '%' || :name || '%'")
    fun filterByName(name: String): List<PokemonEntity>

    @Query("SELECT * FROM pokemon_summary")
    fun getAll(): List<PokemonEntity>

    @Query("SELECT * FROM pokemon_summary")
    fun getAllLiveData(): LiveData<List<PokemonEntity>>

    @Query(
        "SELECT * FROM pokemon_summary WHERE " +
                "name LIKE '%' || :name || '%' AND " +
                "(type_1 in (:types) OR type_2 in (:types)) AND " +
                "sub_name in (:subNames) AND " +
                "generation in (:generation)"
    )
    fun filterLiveDataByFilter(
        name: String,
        types: LinkedHashSet<PokemonType>,
        subNames: LinkedHashSet<PokemonRegionalVariant>,
        generation: LinkedHashSet<Int>
    ): LiveData<List<PokemonEntity>>

    /**
     * 根据名字搜索
     */
    @Query("SELECT * FROM pokemon_summary WHERE name LIKE '%' || :name || '%'")
    fun filterLiveDataByName(name: String): LiveData<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon_summary WHERE dex_number in (:dexNumber)")
    fun loadAllByDexNumber(dexNumber: IntArray): List<PokemonEntity>

    @Query("SELECT * FROM pokemon_summary WHERE dex_number = :dexNumber AND sub_name = ''")
    fun findByDexNumber(dexNumber: Int): PokemonEntity

    @Query("SELECT * FROM pokemon_summary WHERE name LIKE '%' || :name || '%'")
    fun loadAllByName(name: String): List<PokemonEntity>

    @Insert
    fun insertAll(vararg pokemons: PokemonEntity)

    @Delete
    fun delete(pokemons: PokemonEntity)
}