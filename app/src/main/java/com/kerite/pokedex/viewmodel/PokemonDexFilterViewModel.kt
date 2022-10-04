package com.kerite.pokedex.viewmodel

import androidx.lifecycle.ViewModel
import com.kerite.pokedex.COUNT_GENERATION
import com.kerite.pokedex.model.enums.PokemonRegionalVariant
import com.kerite.pokedex.model.enums.PokemonType
import com.kerite.pokedex.util.extension.toLinkedHashSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Suppress("unused")
class PokemonDexFilterViewModel : ViewModel() {
    private val mFilterMode =
        MutableStateFlow(false)
    private val mSearchWord =
        MutableStateFlow("")
    private val mFilterType =
        MutableStateFlow(PokemonType.values().toLinkedHashSet())
    private val mFilterGeneration =
        MutableStateFlow((1..COUNT_GENERATION).toLinkedHashSet())
    private val mFilterRegionalVariant =
        MutableStateFlow(PokemonRegionalVariant.values().toLinkedHashSet())

    val filterModeEnabled: StateFlow<Boolean>
        get() = mFilterMode
    val filterType: StateFlow<LinkedHashSet<PokemonType>>
        get() = mFilterType
    val filterGeneration: StateFlow<LinkedHashSet<Int>>
        get() = mFilterGeneration
    val filterRegionalVariant: StateFlow<LinkedHashSet<PokemonRegionalVariant>>
        get() = mFilterRegionalVariant
    val searchWord: StateFlow<String> get() = mSearchWord

    fun addGeneration(value: Int) {
        mFilterGeneration.value.add(value)
    }

    fun removeGeneration(value: Int) {
        mFilterGeneration.value.remove(value)
    }

    fun addPokemonType(value: PokemonType) {
        mFilterType.value.add(value)
    }

    fun removePokemonType(value: PokemonType) {
        mFilterType.value.remove(value)
    }

    fun addRegionalVariant(value: PokemonRegionalVariant) {
        mFilterRegionalVariant.value.add(value)
    }

    fun removeRegionalVariant(value: PokemonRegionalVariant) {
        mFilterRegionalVariant.value.remove(value)
    }

    fun updateSearchWord(word: String) {
        mSearchWord.value = word
    }
}
