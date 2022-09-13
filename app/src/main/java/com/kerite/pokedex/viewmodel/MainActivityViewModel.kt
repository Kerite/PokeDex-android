package com.kerite.pokedex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    private val mutableSearchFilter = MutableLiveData<String>()
    val searchFilter: LiveData<String> get() = mutableSearchFilter

    fun search(filter: String) {
        mutableSearchFilter.value = filter
    }
}