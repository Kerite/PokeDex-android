package com.kerite.pokedex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kerite.pokedex.COUNT_GENERATION
import com.kerite.pokedex.model.enums.PokemonRegionalVariant
import com.kerite.pokedex.model.enums.PokemonType

class PokemonDexFilterViewModel() : ViewModel() {
    private val mFilterMode: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    private val mFilterType: MutableLiveData<Array<PokemonType>> by lazy {
        MutableLiveData<Array<PokemonType>>(emptyArray())
    }
    private val mFilterGeneration: MutableLiveData<Array<Int>> by lazy {
        MutableLiveData<Array<Int>>(emptyArray())
    }
    private val mFilterRegionalVariant: MutableLiveData<Array<PokemonRegionalVariant>> by lazy {
        MutableLiveData<Array<PokemonRegionalVariant>>(emptyArray())
    }
    val filterModeEnabled: LiveData<Boolean> get() = mFilterMode
    val filterType: LiveData<Array<PokemonType>> get() = mFilterType
    val filterGeneration: LiveData<Array<Int>> get() = mFilterGeneration
    val filterRegionalVariant: LiveData<Array<PokemonRegionalVariant>> get() = mFilterRegionalVariant
}
