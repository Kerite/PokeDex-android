package com.kerite.pokedex.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kerite.pokedex.dao.PokemonDao
import com.kerite.pokedex.entity.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var pokemonDatabase: PokemonDatabase? = null

        fun getInstance(context: Context): PokemonDatabase {
            return pokemonDatabase ?: synchronized(this) {
                pokemonDatabase
                    ?: Room.databaseBuilder(context, PokemonDatabase::class.java, "pokedex.db")
//                        .addCallback(object : Callback() {
//                            override fun onCreate(db: SupportSQLiteDatabase) {
//                                super.onCreate(db)
//                                val request =
//                                    OneTimeWorkRequestBuilder<DatabaseInitialWork>()
//                                        .build()
//                                WorkManager.getInstance(context).enqueue(request)
//                            }
//                        })
                        .createFromAsset("database/pokedex.db")
                        .build()
            }
        }
    }

    abstract fun pokemonDao(): PokemonDao
}