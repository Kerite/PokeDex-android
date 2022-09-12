package com.kerite.pokedex

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.kerite.pokedex.model.enums.PokemonType

class PokedexApplication : Application() {
    companion object {
        private val typeImageCache: HashMap<String, Bitmap> = HashMap()

        fun getTypeImage(type: PokemonType): Bitmap {
            val result = typeImageCache[type.typeTag]
            return result
                ?: BitmapFactory.decodeStream(PokedexApplication::class.java.getResourceAsStream("/res/drawable/${type.typeTag}.png"))
        }
    }
}