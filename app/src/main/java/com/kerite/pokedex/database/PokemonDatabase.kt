package com.kerite.pokedex.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kerite.pokedex.dao.PokemonDao
import com.kerite.pokedex.dao.PokemonDetailsDao
import com.kerite.pokedex.entity.PokemonDetailsEntity
import com.kerite.pokedex.entity.PokemonEntity

@Database(
    entities = [
        PokemonEntity::class,
        PokemonDetailsEntity::class
    ], version = 1,
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

    abstract fun pokemonDetailsDao(): PokemonDetailsDao
}
