package com.kerite.pokedex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.kerite.fission.android.BaseActivity
import com.kerite.fission.android.extensions.shortToast
import com.kerite.pokedex.BuildConfig

private typealias ActivityInflate<T> = (LayoutInflater) -> T

abstract class PokeDexBaseActivity<VB : ViewBinding>(
    inflate: ActivityInflate<VB>
) : BaseActivity<VB>(inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
//        Timber.tag(lifeCycleLogTag).d("onCreate")
        super.onCreate(savedInstanceState)

        // DEBUG下保持屏幕常亮
        if (BuildConfig.DEBUG) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            shortToast("这是个用于调试的内部版本")
        }
    }
}