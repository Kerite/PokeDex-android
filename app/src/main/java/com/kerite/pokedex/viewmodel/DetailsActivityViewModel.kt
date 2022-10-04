package com.kerite.pokedex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kerite.pokedex.database.PokemonDatabase
import com.kerite.pokedex.model.PokemonDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val db = PokemonDatabase.getInstance(application)
    private val mPokemonDexNumber = MutableStateFlow(1)
    private val mPokemonSubIndex = MutableStateFlow(0)
    val pokemonDetails = mPokemonDexNumber.flatMapLatest {
        db.pokemonDetailsDao().findByDexNumber(it)
    }.distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val currentPokemonDetail = combine(
        pokemonDetails, mPokemonSubIndex
    ) { details, index ->
        if (details.isEmpty()) {
            return@combine PokemonDetails.None
        }
        return@combine PokemonDetails.Found(details[index])
    }
    val currentPokemonSubIndex = mPokemonSubIndex.asStateFlow()

    init {
        viewModelScope.launch {
            pokemonDetails.collect {
                mPokemonSubIndex.value = 0
            }
        }
    }

    fun changePokemonDexNumber(dexNumber: Int) {
        mPokemonDexNumber.value = dexNumber
    }

    fun changePokemonIndex(index: Int): Boolean {
        if (index >= 0 && index < pokemonDetails.value.size) {
            mPokemonSubIndex.value = index
            return true
        }
        return false
    }

    fun changePokemonIndex(formName: String?): Boolean {
        val index = pokemonDetails.value.indexOfFirst {
            it.formName == formName
        }
        if (index == -1) {
            return false
        }
        mPokemonSubIndex.value = index
        return true
    }
}