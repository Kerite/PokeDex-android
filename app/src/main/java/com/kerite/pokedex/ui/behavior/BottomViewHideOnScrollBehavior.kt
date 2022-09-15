package com.kerite.pokedex.ui.behavior

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.google.android.material.motion.MotionUtils
import kotlin.coroutines.coroutineContext

class BottomViewHideOnScrollBehavior<V : View> : CoordinatorLayout.Behavior<V> {
    @JvmOverloads
    constructor(context: Context, attr: AttributeSet? = null) : super(context, attr) {
        this.animationUpdateListeners = LinkedHashSet()
        this.mCurrentState = STATE_UP
    }

    constructor() : super() {
        this.animationUpdateListeners = LinkedHashSet()
        this.mCurrentState = STATE_UP
    }

    private val animationUpdateListeners: LinkedHashSet<OnAnimationUpdateListener>
    private var mCurrentState: Int
    private var currentAnimator: ViewPropertyAnimator? = null
    private var height = 0

    private var slideUpDuration = 0
    private var slideDownDuration = 0

    private lateinit var slideUpAnimInterpolator: TimeInterpolator
    private lateinit var slideDownAnimInterpolator: TimeInterpolator

    override fun onLayoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int): Boolean {
        val params = child.layoutParams as ViewGroup.MarginLayoutParams
        height = params.bottomMargin + child.measuredHeight
        slideUpDuration = MotionUtils.resolveThemeDuration(
            child.context,
            com.google.android.material.R.attr.motionDurationLong2,
            DEFAULT_SLIDE_UP_DURATION
        )
        slideDownDuration = MotionUtils.resolveThemeDuration(
            child.context,
            com.google.android.material.R.attr.motionDurationMedium4,
            DEFAULT_SLIDE_DOWN_DURATION
        )
        slideUpAnimInterpolator = MotionUtils.resolveThemeInterpolator(
            child.context,
            com.google.android.material.R.attr.motionEasingEmphasizedInterpolator,
            LinearOutSlowInInterpolator()
        )
        slideDownAnimInterpolator = MotionUtils.resolveThemeInterpolator(
            child.context,
            com.google.android.material.R.attr.motionEasingEmphasizedInterpolator,
            FastOutLinearInInterpolator()
        )
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        if (dyConsumed > 0) {
            slideDown(child)
        } else if (dyConsumed < 0) {
            slideUp(child)
        }
    }

    private fun slideDown(view: V, animate: Boolean = true) {
        if (mCurrentState == STATE_DOWN) return

        currentAnimator?.cancel().also {
            view.clearAnimation()
        }
        mCurrentState = STATE_DOWN
        val targetTranslationY = height.toFloat()
        if (animate) {
            doAnimate(
                view,
                targetTranslationY,
                slideDownDuration.toLong(),
                slideDownAnimInterpolator
            )
        } else {
            view.translationY = targetTranslationY
        }
    }

    private fun slideUp(view: V, animate: Boolean = true) {
        if (mCurrentState == STATE_UP) return

        currentAnimator?.cancel().also {
            view.clearAnimation()
        }
        mCurrentState = STATE_UP
        val targetTranslationY = 0f
        if (animate) {
            doAnimate(view, targetTranslationY, slideUpDuration.toLong(), slideUpAnimInterpolator)
        }
    }

    private fun doAnimate(
        view: V,
        targetTranslationY: Float,
        duration: Long,
        interpolator: TimeInterpolator
    ) {
        currentAnimator = view.animate()
            .translationY(targetTranslationY)
            .setInterpolator(interpolator)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    currentAnimator = null
                }
            })
            .setUpdateListener { animation ->
                animationUpdateListeners.forEach {
                    it.onAnimationUpdated(
                        if (targetTranslationY == 0f)
                            (1 - animation.animatedFraction) * height
                        else
                            animation.animatedFraction * height,
                        if (targetTranslationY == 0f) STATE_UP else STATE_DOWN
                    )
                }
            }
    }

    interface OnAnimationUpdateListener {
        fun onAnimationUpdated(offset: Float, state: Int)
    }

    fun addOnAnimationUpdateListener(listener: OnAnimationUpdateListener) {
        animationUpdateListeners.add(listener)
    }

    fun removeOnAnimationUpdateListener(listener: OnAnimationUpdateListener) {
        animationUpdateListeners.remove(listener)
    }

    companion object {
        const val STATE_UP = 1
        const val STATE_DOWN = 2

        const val DEFAULT_SLIDE_UP_DURATION = 225
        const val DEFAULT_SLIDE_DOWN_DURATION = 175
    }
}