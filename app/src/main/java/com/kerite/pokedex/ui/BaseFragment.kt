package com.kerite.pokedex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

private typealias FragmentInflate<T> = (LayoutInflater) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: FragmentInflate<VB>
) : Fragment() {
    private var mBinding: VB? = null

    val binding: VB get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}