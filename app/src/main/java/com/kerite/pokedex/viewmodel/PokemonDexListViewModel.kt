package com.kerite.pokedex.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.kerite.pokedex.database.PokemonDatabase
import com.kerite.pokedex.entity.PokemonEntity
import com.kerite.pokedex.model.PokemonSearchFilter

class PokemonDexListViewModel(application: Application) : AndroidViewModel(application) {
    private val db = PokemonDatabase.getInstance(application)
    private val searchFilter = MutableLiveData(
        PokemonSearchFilter(
            "", emptyArray(), emptyArray(), emptyArray()
        )
    )
    private val pokemonList = Transformations.switchMap(searchFilter) { filter ->
        if (filter.name.isBlank()) {
            Log.d("PokemonDexListViewModel", "filter is blank")
            return@switchMap db.pokemonDao().getAllLiveData()
        } else {
            Log.d("PokemonDexListViewModel", "filter is not blank")
            return@switchMap db.pokemonDao()
                .filterLiveDataByFilter(filter.name, filter.types, filter.regionalVariant)
        }
    }

    fun getPokemonList(): LiveData<List<PokemonEntity>> {
        return pokemonList
    }

    fun setFilter(filter: PokemonSearchFilter) {
        searchFilter.value = filter
    }
}