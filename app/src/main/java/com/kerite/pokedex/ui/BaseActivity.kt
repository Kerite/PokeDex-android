package com.kerite.pokedex.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

private typealias ActivityInflate<T> = (LayoutInflater) -> T

abstract class BaseActivity<VB : ViewBinding>(
    private val inflate: ActivityInflate<VB>
) : AppCompatActivity() {
    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!this::binding.isInitialized) {
            binding = inflate(layoutInflater)
        }
        setContentView(binding.root)

        // DEBUG下保持屏幕常亮
//        if (BuildConfig.DEBUG) {
//            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
//            Toast.makeText(applicationContext, "当前为调试模式", Toast.LENGTH_SHORT).show()
//        }
    }

    companion object {

    }
}