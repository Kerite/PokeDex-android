package com.kerite.pokedex.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.kerite.pokedex.util.inflateBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    private var mBinding: VB? = null

    val binding: VB get() = checkNotNull(mBinding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = inflateBinding(inflater)
        onInitView(savedInstanceState)
        return binding.root
    }

    open fun onInitView(savedInstanceState: Bundle?) {}

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
}