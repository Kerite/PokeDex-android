package com.kerite.pokedex.ui.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.MenuItem.OnActionExpandListener
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kerite.pokedex.R
import com.kerite.pokedex.customview.BackPressedSearchView
import com.kerite.pokedex.databinding.FragmentPokemonDexBinding
import com.kerite.pokedex.recyclers.PokemonDexRecyclerAdapter
import com.kerite.pokedex.ui.BaseFragment
import com.kerite.pokedex.viewmodel.MainActivityViewModel
import com.kerite.pokedex.viewmodel.SearchViewModel
import com.kerite.pokedex.viewmodel.PokemonDexListAndFilterViewModel
import kotlinx.coroutines.launch

class PokemonDexFragment : BaseFragment<FragmentPokemonDexBinding>(), MenuProvider {
    private lateinit var pokemonDexListAndFilterViewModel: PokemonDexListAndFilterViewModel
    private val activityViewModel: SearchViewModel by activityViewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private var mShowFilter: Boolean = false
    private var currentAnimator: ViewPropertyAnimator? = null
    private val showFilterAnimInterpolator: TimeInterpolator = LinearOutSlowInInterpolator()
    private val hideFilterAnimInterpolator: TimeInterpolator = FastOutLinearInInterpolator()

    override fun onInitView(savedInstanceState: Bundle?) {
        setupRecyclerView()
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.apply {
            pokemonDexFilterFab.setOnClickListener {
                toggleFilter(true)
            }
            // 观察浮动按钮的显示与否
            // pokemonDexFilterViewModel.filterModeEnabled.observe(requireActivity()) { filterModeEnabled ->
            //      pokemonDexFilterFab.visibility = if (filterModeEnabled) View.GONE else View.VISIBLE
            // }
            // 同步 浮动按钮 和 BottomNavigationView
            mainActivityViewModel.bottomOffset.observe(this@PokemonDexFragment) { offset ->
                pokemonDexFilterFab.translationY = offset
            }
        }
    }

    fun toggleFilter(animated: Boolean = false) {
        binding.pokemonDexFilterFragmentContainer.apply {
            currentAnimator?.cancel().also {
                binding.pokemonDexFilterFragmentContainer.clearAnimation()
            }
            mShowFilter = mShowFilter.not()
            val targetTranslationX = if (mShowFilter)
                -binding.pokemonDexFilterFragmentContainer.width.toFloat()
            else
                0f
            if (animated) {
                currentAnimator = animate()
                    .translationX(targetTranslationX)
                    .setInterpolator(if (mShowFilter) showFilterAnimInterpolator else hideFilterAnimInterpolator)
                    .setDuration(if (mShowFilter) 225 else 175)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            currentAnimator = null
                        }
                    })
            } else {
                translationX = targetTranslationX
            }
        }

    }

    private fun setupRecyclerView() {
        binding.pokemonDexList.apply {
            pokemonDexListAndFilterViewModel =
                ViewModelProvider(requireActivity())[PokemonDexListAndFilterViewModel::class.java]
            val adapter = PokemonDexRecyclerAdapter()
            layoutManager = LinearLayoutManager(this.context)
            this.adapter = adapter

            lifecycleScope.launch {
                pokemonDexListAndFilterViewModel.pokemonList.collect {
                    adapter.submitList(it)
                }
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_search_filter, menu)

        // 定义搜索操作
        val searchMenu = menu.findItem(R.id.action_search)
        searchMenu.setOnActionExpandListener(object : OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean = true
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean = true
        })
        val searchView = searchMenu.actionView as BackPressedSearchView
        searchView.queryHint = "Search Pokemon"
        searchView.setOnCloseListener {
            Log.d("searchView", "closed")
            return@setOnCloseListener false
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                pokemonDexListAndFilterViewModel.updateSearchWord(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                pokemonDexListAndFilterViewModel.updateSearchWord(newText ?: "")
                return true
            }
        })
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }

    companion object {
        const val STATE_FILTER_SHOW = 1
        const val STATE_FILTER_HIDE = 2
    }
}
