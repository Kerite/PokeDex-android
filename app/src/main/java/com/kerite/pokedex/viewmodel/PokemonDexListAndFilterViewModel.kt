package com.kerite.pokedex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kerite.fission.extensions.addValue
import com.kerite.fission.extensions.removeValue
import com.kerite.pokedex.COUNT_GENERATION
import com.kerite.pokedex.database.PokemonDatabase
import com.kerite.pokedex.model.PokemonSearchFilter
import com.kerite.pokedex.model.enums.PokemonRegionalVariant
import com.kerite.pokedex.model.enums.PokemonType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import timber.log.Timber

class PokemonDexListAndFilterViewModel(
    application: Application,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {
    private val db = PokemonDatabase.getInstance(application)
    val searchWord = savedStateHandle.getStateFlow(STATE_SEARCH, "")
    private val mFilterType = MutableStateFlow(setOf(*PokemonType.values()))
    private val mFilterGeneration = MutableStateFlow(
        setOf(
            *(1..COUNT_GENERATION).toList().toTypedArray()
        )
    )
    private val mFilterRegionalVariant = MutableStateFlow(setOf(*PokemonRegionalVariant.values()))
    val searchFilter = combine(
        searchWord, mFilterType, mFilterRegionalVariant, mFilterGeneration,
    ) { query, types, regionalVariant, generation ->
//        Log.d("FilterViewModel", "filter Updated")
        if (query.isNotBlank()) {
            // 过滤名称和其它
            PokemonSearchFilter.AdvancedFilterWithQuery(
                query, types, regionalVariant, generation
            )
        } else {
            // 只过滤其它，不过滤名字
            PokemonSearchFilter.AdvancedFilter(
                types, regionalVariant, generation
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, PokemonSearchFilter.ShowAll)

    @OptIn(ExperimentalCoroutinesApi::class)
    val pokemonList = searchFilter.flatMapLatest { searchFilter ->
        Timber.tag("PokemonListFilter").d("SearchFilter Updated To %s", searchFilter)
        when (searchFilter) {
            is PokemonSearchFilter.ShowAll -> {
                db.pokemonDao().getAllFlow()
            }
            is PokemonSearchFilter.NameFilter -> {
                db.pokemonDao().filterFlowByName(searchFilter.name)
            }
            is PokemonSearchFilter.AdvancedFilter -> {
                db.pokemonDao().filterFlowByFilter(
                    searchFilter.types,
                    searchFilter.regionalVariant,
                    searchFilter.generation
                )
            }
            is PokemonSearchFilter.AdvancedFilterWithQuery -> {
                db.pokemonDao().filterFlowByNameAndFilter(
                    searchFilter.name,
                    searchFilter.types,
                    searchFilter.regionalVariant,
                    searchFilter.generation
                )
            }
        }
    }
    val filterType: StateFlow<Set<PokemonType>>
        get() = mFilterType
    val filterGeneration: StateFlow<Set<Int>>
        get() = mFilterGeneration
    val filterRegionalVariant: StateFlow<Set<PokemonRegionalVariant>>
        get() = mFilterRegionalVariant

    fun addGeneration(value: Int) {
        mFilterGeneration.addValue(value)
    }

    fun removeGeneration(value: Int) {
        mFilterGeneration.removeValue(value)
    }

    fun addPokemonType(value: PokemonType) {
        mFilterType.addValue(value)
    }

    fun removePokemonType(value: PokemonType) {
        mFilterType.removeValue(value)
    }

    fun addRegionalVariant(value: PokemonRegionalVariant) {
        mFilterRegionalVariant.addValue(value)
    }

    fun removeRegionalVariant(value: PokemonRegionalVariant) {
        mFilterRegionalVariant.removeValue(value)
    }

    fun updateSearchWord(word: String) {
        savedStateHandle[STATE_SEARCH] = word
    }

    companion object {
        const val STATE_SEARCH = "state_search"
    }
}