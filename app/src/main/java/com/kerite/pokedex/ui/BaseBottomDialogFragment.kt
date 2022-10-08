package com.kerite.pokedex.ui

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.animation.doOnEnd
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.motion.MotionUtils
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.kerite.fission.android.ViewBindingInflate
import com.kerite.pokedex.R
import com.kerite.pokedex.util.dp

abstract class BaseBottomDialogFragment<VB : ViewBinding>(
    private val inflate: ViewBindingInflate<VB>,
    private val initPadding: Boolean = true
) : BottomSheetDialogFragment() {
    val binding get() = mBinding!!

    private var mBinding: VB? = null
    private var mAnimator: ValueAnimator? = null
    private val heightInterpolator = FastOutSlowInInterpolator()
    private lateinit var mDialogBehavior: BottomSheetBehavior<FrameLayout>
    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                val newDrawable = createMaterialShape(bottomSheet)
                ViewCompat.setBackground(bottomSheet, newDrawable)
            }
        }
    }
    private val mLayoutChangeListener =
        View.OnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if ((bottom - top) != (oldBottom - oldTop)) {
                animateHeight(
                    from = oldBottom - oldTop,
                    to = bottom - top
                )
            }
        }

    override fun onCreateDialog(
        savedInstanceState: Bundle?
    ): Dialog = object : BottomSheetDialog(requireContext(), theme) {
        override fun onAttachedToWindow() {
            super.onAttachedToWindow()

            window?.let {
                it.attributes.windowAnimations = R.style.BottomDialogAnimation
                WindowCompat.setDecorFitsSystemWindows(it, false)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    it.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                    it.attributes.blurBehindRadius = 64
                    it.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                }
            }
        }
    }.apply {
        behavior.addBottomSheetCallback(mBottomSheetBehaviorCallback)
        mDialogBehavior = behavior
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflate(inflater).apply {
            mBinding = this
        }.root.apply {
            if (initPadding) {
                setPadding(
                    paddingLeft + resources.getDimension(R.dimen.margin_middle).toInt(),
                    paddingTop + 32.dp,
                    paddingRight + resources.getDimension(R.dimen.margin_middle).toInt(),
                    paddingBottom
                )
            }
        }
    }

    override fun onDestroyView() {
        mAnimator?.cancel()
        mBinding = null
        super.onDestroyView()
    }

    private fun createMaterialShape(bottomSheetView: View): MaterialShapeDrawable {
        val shapeAppearanceModel =
            ShapeAppearanceModel.builder(context, 0, R.style.PokeDex_Widget_BottomSheetAppearance)
                .build()

        val oldShapeDrawable = bottomSheetView.background as MaterialShapeDrawable
        return MaterialShapeDrawable(shapeAppearanceModel).apply {
            initializeElevationOverlay(requireContext())
            fillColor = oldShapeDrawable.fillColor
            tintList = oldShapeDrawable.tintList
            elevation = oldShapeDrawable.elevation
            strokeColor = oldShapeDrawable.strokeColor
            strokeWidth = oldShapeDrawable.strokeWidth
        }
    }

    private fun animateHeight(from: Int, to: Int) {
        mAnimator?.cancel()
        mAnimator = ObjectAnimator.ofFloat(0f, 1f).apply {
            duration = MotionUtils.resolveThemeDuration(
                requireContext(),
                android.R.attr.animationDuration,
                350
            ).toLong()
            interpolator = heightInterpolator
            addUpdateListener {
                val scale = it.animatedValue as Float
                val newHeight = ((to - from) * scale + from).toInt()
                mDialogBehavior.peekHeight = newHeight
            }
            start()
        }
    }

    private fun enqueueAnimation(action: () -> Unit) {
        if (mAnimator == null) {
            action()
            return
        }
        mAnimator?.let {
            if (!it.isRunning) action()
            else it.doOnEnd { action() }
        }
    }
}