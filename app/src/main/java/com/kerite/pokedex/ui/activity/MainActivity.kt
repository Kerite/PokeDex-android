package com.kerite.pokedex.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.kerite.fission.android.extensions.startActivity
import com.kerite.fission.android.ui.SimpleFragmentViewPagerAdapter
import com.kerite.pokedex.INTENT_POKEMON_LIST
import com.kerite.pokedex.INTENT_TOOL_BOX
import com.kerite.pokedex.R
import com.kerite.pokedex.databinding.ActivityMainBinding
import com.kerite.pokedex.ui.PokeDexBaseActivity
import com.kerite.pokedex.ui.behavior.BottomViewHideOnScrollBehavior
import com.kerite.pokedex.ui.fragment.MainFragment
import com.kerite.pokedex.ui.fragment.PokemonListFragment
import com.kerite.pokedex.ui.fragment.ToolboxFragment
import com.kerite.pokedex.viewmodel.MainActivityViewModel

class MainActivity : PokeDexBaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private lateinit var viewPagerAdapter: SimpleFragmentViewPagerAdapter
    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding.bottomNavigation.behavior.currentState == BottomViewHideOnScrollBehavior.STATE_DOWN) {
                binding.bottomNavigation.slideUp()
            }
            if (binding.mainFragmentViewPager.currentItem == 0) {
                finish()
            } else {
                binding.mainFragmentViewPager.currentItem = 0
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false

        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        // Setup BottomNavigation
        binding.bottomNavigation.apply {
            behavior.addOnAnimationUpdateListener(object :
                BottomViewHideOnScrollBehavior.OnAnimationUpdateListener {
                override fun onAnimationUpdated(offset: Float, state: Int) {
                    mainActivityViewModel.setOffset(offset)
                    mainActivityViewModel.setState(state)
                }
            })
        }
        binding.apply {
            // 设置Appbar
            setSupportActionBar(toolbar)
            viewPagerAdapter = SimpleFragmentViewPagerAdapter(
                this@MainActivity,
                R.id.mainFragment to { MainFragment() },
                R.id.dexFragment to { PokemonListFragment() },
                R.id.toolboxFragment to { ToolboxFragment() }
            ) {
                binding.appBarLayout.liftOnScrollTargetViewId = if (R.id.dexFragment == it) {
                    R.id.pokemon_dex_list_recycler_view
                } else {
                    View.NO_ID
                }
                bottomNavigation.selectedItemId = it
            }
            viewPagerAdapter.setupViewPager2(mainFragmentViewPager)
            mainFragmentViewPager.isUserInputEnabled = false
//            viewPagerAdapter.setupBottomNavigationView(bottomNavigation)
            (bottomNavigation as NavigationBarView).setOnItemSelectedListener {
                mainFragmentViewPager.setCurrentItem(viewPagerAdapter.getPosition(it.itemId), true)
                true
            }
        }
        onBackPressedDispatcher.addCallback(this, backPressedCallback)
        handleIntent()
    }

    private fun handleIntent() {
        when (intent.action) {
            INTENT_POKEMON_LIST -> binding.mainFragmentViewPager.currentItem = 1
            INTENT_TOOL_BOX -> binding.mainFragmentViewPager.currentItem = 2
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity<PokeDexSettingsActivity>()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(STATE_PAGE, binding.mainFragmentViewPager.currentItem)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        savedInstanceState?.apply {
            binding.mainFragmentViewPager.currentItem =
                savedInstanceState.getInt(STATE_PAGE, 0)
        }
    }

    companion object {
        const val STATE_PAGE = "current_page"
    }
}