package com.kerite.pokedex.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.kerite.pokedex.BuildConfig

private typealias ActivityInflate<T> = (LayoutInflater) -> T

abstract class BaseActivity<VB : ViewBinding>(
    private val inflate: ActivityInflate<VB>
) : AppCompatActivity() {
    lateinit var binding: VB
//    private val lifeCycleLogTag = "${this.javaClass.simpleName}-LifeCycle"

    override fun onCreate(savedInstanceState: Bundle?) {
//        Timber.tag(lifeCycleLogTag).d("onCreate")
        super.onCreate(savedInstanceState)
        if (!this::binding.isInitialized) {
            binding = inflate(layoutInflater)
        }
        setContentView(binding.root)

        // DEBUG下保持屏幕常亮
        if (BuildConfig.DEBUG) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            Toast.makeText(applicationContext, "这是个用于调试的内部版本", Toast.LENGTH_SHORT).show()
        }
    }

//    override fun onStart() {
//        Timber.tag(lifeCycleLogTag).d("onStart")
//        super.onStart()
//    }
//
//    override fun onResume() {
//        Timber.tag(lifeCycleLogTag).d("onResume")
//        super.onResume()
//    }
//
//    override fun onPause() {
//        Timber.tag(lifeCycleLogTag).d("onPause")
//        super.onPause()
//    }
//
//    override fun onStop() {
//        Timber.tag(lifeCycleLogTag).d("onStop")
//        super.onStop()
//    }
//
//    override fun onRestart() {
//        Timber.tag(lifeCycleLogTag).d("onRestart")
//        super.onRestart()
//    }
//
//    override fun onDestroy() {
//        Timber.tag(lifeCycleLogTag).d("onDestroy")
//        super.onDestroy()
//    }
//
//    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
//        Timber.tag(lifeCycleLogTag).d("onSaveInstanceState(Bundle, PersistableBundle)")
//        super.onSaveInstanceState(outState, outPersistentState)
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        Timber.tag(lifeCycleLogTag).d("onSaveInstanceState(Bundle)")
//        super.onSaveInstanceState(outState)
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
//        Timber.tag(lifeCycleLogTag).d("onRestoreInstanceState(Bundle, PersistableBundle)")
//        super.onRestoreInstanceState(savedInstanceState, persistentState)
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        Timber.tag(lifeCycleLogTag).d("onRestoreInstanceState(Bundle)")
//        super.onRestoreInstanceState(savedInstanceState)
//    }

    /**
     * 打开指定的 Activity
     */
    fun startActivity(target: Class<out BaseActivity<*>>) {
        startActivity(Intent(this, target))
    }
}