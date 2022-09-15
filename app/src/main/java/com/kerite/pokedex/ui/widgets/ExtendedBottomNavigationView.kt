package com.kerite.pokedex.ui.widgets

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewPropertyAnimator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kerite.pokedex.ui.behavior.BottomViewHideOnScrollBehavior
import com.kerite.pokedex.util.extension.applySystemAnimatorScale

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