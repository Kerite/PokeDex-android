package com.kerite.pokedex.ui

import android.animation.ObjectAnimator
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class MoveItemAnimator : DefaultItemAnimator() {
    private val animationsMap: MutableMap<RecyclerView.ViewHolder, Animation> = mutableMapOf()
    private val accelerateInterpolate = AccelerateInterpolator()
    private val decelerateInterpolator = DecelerateInterpolator()

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        return super.animateChange(oldHolder, newHolder, fromX, fromY, toX, toY)
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        cancelCurrentAnimationIfExists(holder)
        val enterAnimator = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0.2f, 1f)
        enterAnimator.duration = 150
        enterAnimator.interpolator = decelerateInterpolator
        enterAnimator.doOnEnd {
            animationsMap.remove(holder)
            dispatchAddFinished(holder)
        }
        enterAnimator.start()
        return false
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
//        Timber.d("Animate Remove")
        cancelCurrentAnimationIfExists(holder)
        val exitAnimator = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 1f, 0.2f)
        exitAnimator.duration = 150
        exitAnimator.interpolator = accelerateInterpolate
        exitAnimator.doOnEnd {
            animationsMap.remove(holder)
            dispatchRemoveFinished(holder)
        }
        exitAnimator.start()
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