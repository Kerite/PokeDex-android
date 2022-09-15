package com.kerite.pokedex.converters

import androidx.room.TypeConverter
import com.kerite.pokedex.model.enums.PokemonRegionalVariant

class PokemonRegionalVariantConverter {
    @TypeConverter
    fun toPokemonRegionalVariant(value: String): PokemonRegionalVariant {
        for (enumV in PokemonRegionalVariant.values()) {
            if (enumV.subName == value) {
                return enumV
            }
        }
        return PokemonRegionalVariant.NORMAL
    }

    @TypeConverter
    fun fromPokemonRegionalVariant(enumV: PokemonRegionalVariant): String {
        return enumV.subName
    }
}