package com.kerite.pokedex.util.extension

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.getAndUpdate

inline fun <reified T> MutableStateFlow<Set<T>>.addValue(value: T) =
    this.getAndUpdate { setOf(*(it + value).toTypedArray()) }

inline fun <reified T> MutableStateFlow<Set<T>>.removeValue(value: T) =
    this.getAndUpdate { setOf(*(it - value).toTypedArray()) }