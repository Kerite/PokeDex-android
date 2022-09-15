package com.kerite.pokedex.util.extension

import android.content.Context
import android.view.ViewPropertyAnimator
import android.view.animation.Animation
import androidx.transition.Transition

fun Animation.applySystemAnimatorScale(context: Context) {
    this.duration = (this.duration * context.animatorDurationScale).toLong()
}

/** Scale the duration of this [Transition] by [Context.animatorDurationScale] */
fun Transition.applySystemAnimatorScale(context: Context) {
    // End layout of cover expanding animation tends to break when the transition is less than ~25ms
    this.duration = (this.duration * context.animatorDurationScale).toLong().coerceAtLeast(25)
}

/** Scale the duration of this [ViewPropertyAnimator] by [Context.animatorDurationScale] */
fun ViewPropertyAnimator.applySystemAnimatorScale(context: Context): ViewPropertyAnimator = apply {
    this.duration = (this.duration * context.animatorDurationScale).toLong()
}