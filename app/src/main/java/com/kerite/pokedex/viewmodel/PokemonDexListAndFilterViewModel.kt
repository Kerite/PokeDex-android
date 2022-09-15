package com.kerite.pokedex.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kerite.pokedex.COUNT_GENERATION
import com.kerite.pokedex.database.PokemonDatabase
import com.kerite.pokedex.model.PokemonSearchFilter
import com.kerite.pokedex.model.enums.PokemonRegionalVariant
import com.kerite.pokedex.model.enums.PokemonType
import com.kerite.pokedex.util.extension.toLinkedHashSet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class PokemonDexListAndFilterViewModel(application: Application) : AndroidViewModel(application) {
    private val db = PokemonDatabase.getInstance(application)
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
    private val searchFilter = combine(
        mFilterMode, mSearchWord, mFilterType, mFilterRegionalVariant, mFilterGeneration,
    ) { advancedMode, query, types, regionalVariant, generation ->
        Log.d("FilterViewModel", "filter Updated")
        if (!advancedMode && query.isBlank()) {
            return@combine PokemonSearchFilter.ShowAll
        } else if (!advancedMode && query.isNotBlank()) {
            return@combine PokemonSearchFilter.NameFilter(
                query
            )
        } else if (advancedMode && query.isNotBlank()) {
            return@combine PokemonSearchFilter.AdvancedFilterWithQuery(
                query, types, regionalVariant, generation
            )
        } else {
            return@combine PokemonSearchFilter.AdvancedFilter(
                types, regionalVariant, generation
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, PokemonSearchFilter.ShowAll)

    @OptIn(ExperimentalCoroutinesApi::class)
    val pokemonList = searchFilter.flatMapLatest { filter ->
        Log.d("SearchFilter", filter.toString())
        val flow = when (filter) {
            is PokemonSearchFilter.ShowAll -> {
                Log.d("SearchMethod", "ShowAll")
                db.pokemonDao().getAllFlow()
            }
            is PokemonSearchFilter.NameFilter -> {
                Log.d("SearchMethod", "NameQuery")
                db.pokemonDao().filterFlowByName(filter.name)
            }
            is PokemonSearchFilter.AdvancedFilterWithQuery -> {
                Log.d("SearchMethod", "Filter")
                db.pokemonDao().filterFlowByNameAndFilter(
                    filter.name,
                    filter.types,
                    filter.regionalVariant,
                    filter.generation
                )
            }
            is PokemonSearchFilter.AdvancedFilter -> {
                db.pokemonDao().filterFlowByFilter(
                    filter.types,
                    filter.regionalVariant,
                    filter.generation
                )
            }
        }
        return@flatMapLatest flow
    }
    val advancedFilterMode: StateFlow<Boolean>
        get() = mFilterMode
    val filterType: StateFlow<LinkedHashSet<PokemonType>>
        get() = mFilterType
    val filterGeneration: StateFlow<LinkedHashSet<Int>>
        get() = mFilterGeneration
    val filterRegionalVariant: StateFlow<LinkedHashSet<PokemonRegionalVariant>>
        get() = mFilterRegionalVariant
    val searchWord: StateFlow<String>
        get() = mSearchWord

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
        Log.d("SearchWord", word)
        mSearchWord.update { word }
    }
}