package com.kerite.pokedex.ui

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.kerite.pokedex.BuildConfig
import com.kerite.pokedex.util.inflateBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!this::binding.isInitialized) {
            binding = inflateBinding(layoutInflater)
        }
        setContentView(binding.root)

        // DEBUG下保持屏幕常亮
        if (BuildConfig.DEBUG) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            Toast.makeText(applicationContext, "当前为调试模式", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

    }
}