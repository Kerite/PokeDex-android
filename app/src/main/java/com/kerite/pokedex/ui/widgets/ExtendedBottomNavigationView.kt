package com.kerite.pokedex.ui.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kerite.pokedex.ui.behavior.BottomViewHideOnScrollBehavior

class ExtendedBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = com.google.android.material.R.attr.bottomNavigationStyle,
    defStyleRes: Int = com.google.android.material.R.style.Widget_Design_BottomNavigationView
) : BottomNavigationView(context, attrs, defStyle, defStyleRes),
    CoordinatorLayout.AttachedBehavior {
    private val behavior = BottomViewHideOnScrollBehavior<ExtendedBottomNavigationView>()

    override fun getBehavior() = behavior
}