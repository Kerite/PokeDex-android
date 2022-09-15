package com.kerite.pokedex.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.MenuItem.OnActionExpandListener
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kerite.pokedex.R
import com.kerite.pokedex.customview.BackPressedSearchView
import com.kerite.pokedex.databinding.FragmentPokemonDexBinding
import com.kerite.pokedex.model.PokemonSearchFilter
import com.kerite.pokedex.recyclers.PokemonDexRecyclerAdapter
import com.kerite.pokedex.ui.BaseFragment
import com.kerite.pokedex.viewmodel.MainActivityViewModel
import com.kerite.pokedex.viewmodel.SearchViewModel
import com.kerite.pokedex.viewmodel.PokemonDexFilterViewModel
import com.kerite.pokedex.viewmodel.PokemonDexListViewModel

class PokemonDexFragment : BaseFragment<FragmentPokemonDexBinding>(), MenuProvider {
    private lateinit var pokemonListViewModel: PokemonDexListViewModel
    private val activityViewModel: SearchViewModel by activityViewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private val pokemonDexFilterViewModel: PokemonDexFilterViewModel by viewModels()

    override fun onInitView(savedInstanceState: Bundle?) {
        setupRecyclerView()
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.apply {
            pokemonDexFilterFab.setOnClickListener {
                showFilter()
            }
            // 观察浮动按钮的显示与否
//            pokemonDexFilterViewModel.filterModeEnabled.observe(requireActivity()) { filterModeEnabled ->
//                pokemonDexFilterFab.visibility = if (filterModeEnabled) View.GONE else View.VISIBLE
//            }
            mainActivityViewModel.bottomOffset.observe(this@PokemonDexFragment) { offset ->
                pokemonDexFilterFab.translationY = offset
            }
        }
    }

    private fun showFilter() {
        // TODO 显示过滤窗口
    }

    private fun setupRecyclerView() {
        binding.pokemonDexList.apply {
            pokemonListViewModel =
                ViewModelProvider(requireActivity())[PokemonDexListViewModel::class.java]
            val adapter = PokemonDexRecyclerAdapter()
            layoutManager = LinearLayoutManager(this.context)
            this.adapter = adapter
            // 搜索框内容改变
            activityViewModel.searchKeyword.observe(viewLifecycleOwner) { keyword ->
                Log.d("Filter", "Filter Updated to $keyword")
                pokemonListViewModel.setFilter(
                    PokemonSearchFilter(
                        "",
                        pokemonDexFilterViewModel.filterType.value!!,
                        pokemonDexFilterViewModel.filterRegionalVariant.value!!,
                        pokemonDexFilterViewModel.filterGeneration.value!!
                    )
                )
            }
            // 查询结果中的内容改变
            pokemonListViewModel.getPokemonList().observe(viewLifecycleOwner) {
                adapter.submitList(it)
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
                activityViewModel.search(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                activityViewModel.search(newText ?: "")
                return true
            }
        })
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }
}
