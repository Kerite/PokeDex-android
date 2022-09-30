package com.kerite.pokedex.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kerite.fission.extensions.addValue
import com.kerite.fission.extensions.removeValue
import com.kerite.pokedex.COUNT_GENERATION
import com.kerite.pokedex.database.PokemonDatabase
import com.kerite.pokedex.model.PokemonSearchFilter
import com.kerite.pokedex.model.enums.PokemonRegionalVariant
import com.kerite.pokedex.model.enums.PokemonType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonDexListAndFilterViewModel(application: Application) : AndroidViewModel(application) {
    private val db = PokemonDatabase.getInstance(application)
    private val mFilterMode =
        MutableStateFlow(true)

    private val mSearchWord =
        MutableStateFlow("")
    private val mFilterType =
        MutableStateFlow(setOf(*PokemonType.values()))
    private val mFilterGeneration =
        MutableStateFlow(
            setOf(
                *(1..COUNT_GENERATION).toList().toTypedArray()
            )
        )
    private val mFilterRegionalVariant =
        MutableStateFlow(setOf(*PokemonRegionalVariant.values()))
    val searchFilter = combine(
        mFilterMode, mSearchWord, mFilterType, mFilterRegionalVariant, mFilterGeneration,
    ) { advancedMode, query, types, regionalVariant, generation ->
//        Log.d("FilterViewModel", "filter Updated")
        return@combine if (!advancedMode && query.isBlank()) {
            // 显示全部
            PokemonSearchFilter.ShowAll
        } else if (!advancedMode && query.isNotBlank()) {
            // 只过滤名称
            PokemonSearchFilter.NameFilter(query)
        } else if (advancedMode && query.isNotBlank()) {
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
        Log.d("PokemonListFilter", "SearchFilter Updated To $searchFilter")
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

    init {
        viewModelScope.launch {
            mSearchWord.collect {
                Log.d("ViewModel", "SearchWord: $it")
            }
        }
        viewModelScope.launch {
            mFilterGeneration.collect {
                Log.d("ViewModel", "GenerationFilter: $it")
            }
        }
        viewModelScope.launch {
            mFilterType.collect {
                Log.d("ViewModel", "TypeFilter: $it")
            }
        }
        viewModelScope.launch {
            mFilterRegionalVariant.collect {
                Log.d("ViewModel", "FilterRegionalVariant: $it")
            }
        }
    }

    val advancedFilterMode: StateFlow<Boolean>
        get() = mFilterMode
    val filterType: StateFlow<Set<PokemonType>>
        get() = mFilterType
    val filterGeneration: StateFlow<Set<Int>>
        get() = mFilterGeneration
    val filterRegionalVariant: StateFlow<Set<PokemonRegionalVariant>>
        get() = mFilterRegionalVariant
    val searchWord: StateFlow<String>
        get() = mSearchWord

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
        mSearchWord.update { word }
    }

    fun advancedMode() {
        mFilterMode.value = mFilterMode.value.not()
    }
}