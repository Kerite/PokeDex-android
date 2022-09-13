package com.kerite.pokedex.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.kerite.pokedex.database.PokemonDatabase
import com.kerite.pokedex.entity.PokemonEntity
import com.kerite.pokedex.model.PokemonDex
import com.kerite.pokedex.model.enums.PokemonType

class PokemonDexListViewModel(application: Application) : AndroidViewModel(application) {
    private val db = PokemonDatabase.getInstance(application)
    private val filterText = MutableLiveData<String>()
    private val pokemonList = Transformations.switchMap(filterText) {
        if (it.isNullOrBlank()){
            Log.d("PokemonDexListViewModel", "filter is blank")
            return@switchMap db.pokemonDao().getAllLiveData()
        }
        else{
            Log.d("PokemonDexListViewModel", "filter is not blank")
            return@switchMap db.pokemonDao().filterLiveDataByName(it)
        }
    }

    fun getPokemonList(): LiveData<List<PokemonEntity>> {
        return pokemonList
    }

    fun setFilter(filter: String) {
        filterText.value = filter
    }
}