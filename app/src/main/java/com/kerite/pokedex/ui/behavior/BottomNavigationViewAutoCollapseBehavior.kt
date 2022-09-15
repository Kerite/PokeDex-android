package com.kerite.pokedex.ui.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class BottomNavigationViewAutoCollapseBehavior(
    context: Context, attrs: AttributeSet
) : CoordinatorLayout.Behavior<View>(context, attrs) {
    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: View,
        layoutDirection: Int
    ): Boolean {
        (child.layoutParams as CoordinatorLayout.LayoutParams).topMargin =
            parent.measuredHeight - child.measuredHeight
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        val top =
            ((dependency.layoutParams as CoordinatorLayout.LayoutParams).behavior as AppBarLayout.Behavior?)!!.topAndBottomOffset
        //因为BottomNavigation的滑动与ToolBar是反向的，所以取-top值
        child.translationY = -top.toFloat()
        return false
    }
}