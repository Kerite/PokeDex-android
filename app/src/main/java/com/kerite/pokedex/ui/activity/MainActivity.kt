package com.kerite.pokedex.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.kerite.fission.android.extensions.startActivity
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
import com.kerite.pokedex.viewmodel.SearchViewModel

class MainActivity : PokeDexBaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {
    private val searchWordViewModel: SearchViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private var viewPagerAdapter: FragmentStateAdapter? = null
    private val pageCallback: ViewPager2.OnPageChangeCallback = object :
        ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.bottomNavigation.selectedItemId =
                when (position) {
                    0 -> R.id.mainFragment
                    1 -> R.id.dexFragment
                    else -> R.id.toolboxFragment
                }
        }
    }
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
            onBackPressedDispatcher.addCallback(this@MainActivity, backPressedCallback)
        }
        binding.apply {
            // 设置Appbar
            setSupportActionBar(toolbar)
            viewPagerAdapter = object : FragmentStateAdapter(this@MainActivity) {
                override fun getItemCount(): Int {
                    return 3
                }

                override fun createFragment(position: Int): Fragment {
                    return when (position) {
                        0 -> MainFragment()
                        1 -> PokemonListFragment()
                        else -> ToolboxFragment()
                    }
                }
            }
            mainFragmentViewPager.registerOnPageChangeCallback(pageCallback)
            mainFragmentViewPager.adapter = viewPagerAdapter
            mainFragmentViewPager.isUserInputEnabled = false
            (bottomNavigation as NavigationBarView).setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.mainFragment -> mainFragmentViewPager.setCurrentItem(0, true)
                    R.id.dexFragment -> mainFragmentViewPager.setCurrentItem(1, true)
                    R.id.toolboxFragment -> mainFragmentViewPager.setCurrentItem(2, true)
                }
                true
            }
        }
        handleIntent()
    }

    override fun onResume() {
        super.onResume()
        binding.mainFragmentViewPager.registerOnPageChangeCallback(pageCallback)
    }

    override fun onPause() {
        super.onPause()
        binding.mainFragmentViewPager.unregisterOnPageChangeCallback(pageCallback)
    }

    private fun handleIntent() {
        when (intent.action) {
            INTENT_POKEMON_LIST -> binding.mainFragmentViewPager.currentItem = 1
            INTENT_TOOL_BOX -> binding.mainFragmentViewPager.currentItem = 2
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
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