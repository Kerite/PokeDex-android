package com.kerite.pokedex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    private val mutableSearchFilter = MutableLiveData<String>()
    val searchKeyword: LiveData<String> get() = mutableSearchFilter

    fun search(filter: String) {
        mutableSearchFilter.value = filter
    }
}