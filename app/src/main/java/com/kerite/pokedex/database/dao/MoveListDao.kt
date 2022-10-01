package com.kerite.pokedex.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.kerite.pokedex.COUNT_GENERATION
import com.kerite.pokedex.database.dbview.MoveLearnDatabaseView
import com.kerite.pokedex.database.entity.PokeDexMoveEntity
import com.kerite.pokedex.model.enums.MoveCategory
import com.kerite.pokedex.model.enums.PokemonType

@Dao
interface MoveListDao {
    @Query("SELECT * FROM pokemon_move ORDER BY id")
    fun allMoveBasicList(): List<PokeDexMoveEntity>

    @Query(
        "SELECT * FROM pokemon_move WHERE " +
                "(type = :type OR :type = null) AND " +
                "(damage_category = :damageCategory OR :damageCategory = NULL) AND " +
                "(generation >= :minGeneration AND generation <= :maxGeneration) AND " +
                "(touches = :touches OR :touches = NULL) AND " +
                "(protect = :protect OR :protect = NULL) AND " +
                "(magic_coat = :magicCoat OR :magicCoat = NULL) AND " +
                "(snatch = :snatch OR :snatch = NULL) AND " +
                "(mirror_move = :mirrorMove OR :mirrorMove = NULL) AND " +
                "(kings_rock = :kingsRock OR :kingsRock = NULL) AND " +
                "(sound = :sound OR :sound = NULL) AND " +
                "(target = :target OR :target = NULL)"
    )
    fun filterMoveBasic(
        type: PokemonType? = null,
        damageCategory: MoveCategory? = null,
        minGeneration: Int = 0,
        maxGeneration: Int = COUNT_GENERATION,
        touches: Boolean? = null,
        protect: Boolean? = null,
        magicCoat: Boolean? = null,
        snatch: Boolean? = null,
        mirrorMove: Boolean? = null,
        kingsRock: Boolean? = null,
        sound: Boolean? = null,
        target: Int? = null
    ): List<PokeDexMoveEntity>

    @Query(
        "SELECT * FROM MoveLearnDatabaseView WHERE dexNumber = :dexNumber"
    )
    fun filterMoveLearn(dexNumber: Int): List<MoveLearnDatabaseView>
}