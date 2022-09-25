package com.kerite.pokedex.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.kerite.pokedex.INTENT_POKEMON_LIST
import com.kerite.pokedex.INTENT_TOOL_BOX
import com.kerite.pokedex.R
import com.kerite.pokedex.databinding.ActivityMainBinding
import com.kerite.pokedex.ui.BaseActivity
import com.kerite.pokedex.ui.behavior.BottomViewHideOnScrollBehavior
import com.kerite.pokedex.viewmodel.MainActivityViewModel
import com.kerite.pokedex.viewmodel.SearchViewModel

class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {
    private lateinit var navController: NavController
    private val searchWordViewModel: SearchViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false

        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
//        binding.bottomNavigation.applyInsetter {
//            type(navigationBars = true) {
//                padding()
//            }
//        }

        binding.apply {
            // 设置Appbar
            setSupportActionBar(toolbar)
            // 绑定fragmentContainer和navController
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
            navController = navHostFragment.navController
            bottomNavigation.apply {
                // 绑定底部导航栏和导航图
                setupWithNavController(navController)
                behavior.addOnAnimationUpdateListener(object :
                    BottomViewHideOnScrollBehavior.OnAnimationUpdateListener {
                    override fun onAnimationUpdated(offset: Float, state: Int) {
                        mainActivityViewModel.setOffset(offset)
                        mainActivityViewModel.setState(state)
                    }
                })
            }
        }
    }

    fun handleIntent(intent: Intent) {
        when (intent.action) {
            INTENT_POKEMON_LIST -> navController.navigate(R.id.dexFragment)
            INTENT_TOOL_BOX -> navController.navigate(R.id.toolboxFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(PokeDexSettingsActivity::class.java)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}