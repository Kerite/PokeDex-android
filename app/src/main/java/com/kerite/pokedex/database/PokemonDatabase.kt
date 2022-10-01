package com.kerite.pokedex.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kerite.pokedex.database.dao.MoveListDao
import com.kerite.pokedex.database.dao.PokeDexAbilityDao
import com.kerite.pokedex.database.dao.PokemonDao
import com.kerite.pokedex.database.dao.PokemonDetailsDao
import com.kerite.pokedex.database.dbview.MoveLearnDatabaseView
import com.kerite.pokedex.database.entity.*

@Database(
    entities = [
        PokemonEntity::class,
        PokemonDetailsEntity::class,
        PokeDexAbilityEntity::class,
        PokeDexMoveLearnEntity::class,
        PokeDexMoveEntity::class
    ],
    views = [
        MoveLearnDatabaseView::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var pokemonDatabase: PokemonDatabase? = null

        fun getInstance(context: Context): PokemonDatabase {
            return pokemonDatabase ?: synchronized(this) {
                pokemonDatabase
                    ?: Room.databaseBuilder(context, PokemonDatabase::class.java, "pokedex.db")
                        .createFromAsset("database/pokedex.db")
                        .build()
            }
        }
    }

    abstract fun pokemonDao(): PokemonDao

    /**
     * 宝可梦详情 DAO
     */
    abstract fun pokemonDetailsDao(): PokemonDetailsDao

    /**
     * 宝可梦特性列表 DAO
     */
    abstract fun pokemonAbilitySummary(): PokeDexAbilityDao

    abstract fun moveListDao(): MoveListDao
}
