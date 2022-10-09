package com.kerite.pokedex

import com.kerite.pokedex.model.enums.PokemonType
import com.kerite.pokedex.util.PokemonDamageUtils
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testPokemonDamageMultiplier() {
        println("FIRE")
        println("Weakness ${PokemonDamageUtils.getWeaknessAsDefender(PokemonType.FIRE)}")
        println("Normal ${PokemonDamageUtils.getNormalAsDefender(PokemonType.FIRE)}")
        println("Effective ${PokemonDamageUtils.getEffectiveAsDefender(PokemonType.FIRE)}")
        println("FLYING")
        println("Weakness ${PokemonDamageUtils.getWeaknessAsDefender(PokemonType.FLYING)}")
        println("Normal ${PokemonDamageUtils.getNormalAsDefender(PokemonType.FLYING)}")
        println("Effective ${PokemonDamageUtils.getEffectiveAsDefender(PokemonType.FLYING)}")

        println("FLYING AND FIRE")
        println("Weakness ${PokemonDamageUtils.getWeaknessAsDefender(PokemonType.FLYING, PokemonType.FIRE)}")
        println("Normal ${PokemonDamageUtils.getNormalAsDefender(PokemonType.FLYING, PokemonType.FIRE)}")
        println("Effective ${PokemonDamageUtils.getEffectiveAsDefender(PokemonType.FLYING, PokemonType.FIRE)}")

    }
}