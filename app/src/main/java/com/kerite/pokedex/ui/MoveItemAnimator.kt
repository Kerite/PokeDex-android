package com.kerite.pokedex.ui

import android.view.animation.Animation
import androidx.core.animation.AccelerateInterpolator
import androidx.core.animation.Animator
import androidx.core.animation.AnimatorListenerAdapter
import androidx.core.animation.AnimatorSet
import androidx.core.animation.DecelerateInterpolator
import androidx.core.animation.ObjectAnimator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class MoveItemAnimator : DefaultItemAnimator() {
    private val animationsMap: MutableMap<RecyclerView.ViewHolder, Animation> = mutableMapOf()
    private val accelerateInterpolate = AccelerateInterpolator()
    private val decelerateInterpolator = DecelerateInterpolator()

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        preInfo: ItemHolderInfo,
        postInfo: ItemHolderInfo
    ): Boolean {
//        Timber.d("Animate Change")
        cancelCurrentAnimationIfExists(newHolder)
        val animatorSet = AnimatorSet()
        val oldView = oldHolder.itemView
        oldView.scaleX

        val exitAnimator = ObjectAnimator.ofFloat(oldView, "scaleX", 1f, 0f)
        exitAnimator.duration = 1000
        exitAnimator.interpolator = accelerateInterpolate

        val enterAnimator = ObjectAnimator.ofFloat(newHolder.itemView, "scaleX", 0f, 1f)
        enterAnimator.duration = 1000
        enterAnimator.interpolator = decelerateInterpolator

        animatorSet.play(enterAnimator).after(exitAnimator)
        animatorSet.start()

        return false
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder?,
        newHolder: RecyclerView.ViewHolder?,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
//        Timber.d("Animate Change 2")
        return super.animateChange(oldHolder, newHolder, fromX, fromY, toX, toY)
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
//        Timber.d("Animate Add")
        holder?.let {
            cancelCurrentAnimationIfExists(holder)
            val enterAnimator = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0f, 1f)
            enterAnimator.duration = 150
            enterAnimator.interpolator = decelerateInterpolator
            enterAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    animationsMap.remove(it)
                }
            })
            enterAnimator.start()
        }
        return false
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
//        Timber.d("Animate Remove")
        holder?.let {
            cancelCurrentAnimationIfExists(it)
            val exitAnimator = ObjectAnimator.ofFloat(it.itemView, "scaleX", 1f, 0f)
            exitAnimator.duration = 150
            exitAnimator.interpolator = accelerateInterpolate
            exitAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    animationsMap.remove(it)
                }
            })
            exitAnimator.start()
        }
        return false
    }

    private fun cancelCurrentAnimationIfExists(item: RecyclerView.ViewHolder) {
        animationsMap[item]?.cancel()
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {
        super.endAnimation(item)
        cancelCurrentAnimationIfExists(item)
    }

    override fun endAnimations() {
        super.endAnimations()
        animationsMap.values.forEach {
            it.cancel()
        }
    }
}