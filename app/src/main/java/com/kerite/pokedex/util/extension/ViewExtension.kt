package com.kerite.pokedex.util.extension

import android.view.ViewGroup
import androidx.core.view.children

inline fun <reified T> ViewGroup.findChild(): T? = children.find { it is T } as T?