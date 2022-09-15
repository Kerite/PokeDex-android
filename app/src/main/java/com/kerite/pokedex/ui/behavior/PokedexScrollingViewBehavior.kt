package com.kerite.pokedex.ui.behavior

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.appbar.AppBarLayout

class PokedexScrollingViewBehavior @JvmOverloads constructor(
    context: Context, attr: AttributeSet? = null
) : AppBarLayout.ScrollingViewBehavior(context, attr) {
    override fun shouldHeaderOverlapScrollingChild(): Boolean {
        return false
    }
}