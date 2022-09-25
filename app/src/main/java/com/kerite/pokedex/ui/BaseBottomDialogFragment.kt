package com.kerite.pokedex.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kerite.pokedex.R

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
                    it.attributes?.windowAnimations = R.style.BottomDialogAnimation
                    WindowCompat.setDecorFitsSystemWindows(it, false)
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
        return mBinding.root
    }
}