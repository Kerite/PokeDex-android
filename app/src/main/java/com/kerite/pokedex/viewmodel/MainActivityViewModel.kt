package com.kerite.pokedex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kerite.pokedex.SETTINGS_LOW_PERFORMANCE_DEFAULT
import com.kerite.pokedex.SETTINGS_LOW_PERFORMANCE_KEY
import com.kerite.pokedex.settingsDataStore
import com.kerite.pokedex.ui.behavior.BottomViewHideOnScrollBehavior
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val mBottomHidden by lazy {
        MutableLiveData(false)
    }
    private val mBottomOffset: MutableLiveData<Float> by lazy {
        MutableLiveData(0.0f)
    }
    private val mBottomState: MutableLiveData<Int> by lazy {
        MutableLiveData(BottomViewHideOnScrollBehavior.STATE_UP)
    }
    val bottomHidden: LiveData<Boolean> get() = mBottomHidden
    val bottomOffset: LiveData<Float> get() = mBottomOffset
    val bottomState: LiveData<Int> get() = mBottomState
    val performanceModeFlow = application.settingsDataStore.data
        .mapLatest { preference -> preference[SETTINGS_LOW_PERFORMANCE_KEY] }
        .stateIn(viewModelScope, SharingStarted.Eagerly, SETTINGS_LOW_PERFORMANCE_DEFAULT)

    fun setHidden(hidden: Boolean) {
        mBottomHidden.value = hidden
    }

    fun setOffset(offset: Float) {
        mBottomOffset.value = offset
    }

    fun setState(state: Int) {
        mBottomState.value = state
    }
}