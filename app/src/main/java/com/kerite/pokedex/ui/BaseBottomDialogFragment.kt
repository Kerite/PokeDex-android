package com.kerite.pokedex.ui

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kerite.pokedex.R
import com.kerite.pokedex.util.dp

private typealias BottomDialogInflate<T> = (LayoutInflater) -> T

abstract class BaseBottomDialogFragment<VB : ViewBinding>(
    private val inflate: BottomDialogInflate<VB>
) : BottomSheetDialogFragment() {
    private lateinit var mBinding: VB
    val binding get() = mBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        object : BottomSheetDialog(requireContext(), theme) {
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
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!this::mBinding.isInitialized) {
            mBinding = inflate(inflater)
        }
        return mBinding.root.apply {
            setPadding(paddingLeft, paddingTop + 32.dp, paddingRight, paddingBottom)
        }
    }
}