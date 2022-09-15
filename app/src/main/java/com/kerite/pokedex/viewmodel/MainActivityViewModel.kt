package com.kerite.pokedex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kerite.pokedex.ui.behavior.BottomViewHideOnScrollBehavior

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