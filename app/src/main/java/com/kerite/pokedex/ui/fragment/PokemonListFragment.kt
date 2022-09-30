package com.kerite.pokedex.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MenuItem.OnActionExpandListener
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kerite.fission.AntiShaker
import com.kerite.fission.android.BaseFragment
import com.kerite.fission.android.extensions.startActivity
import com.kerite.pokedex.R
import com.kerite.pokedex.databinding.FragmentPokemonDexBinding
import com.kerite.pokedex.recyclers.PokemonDexRecyclerAdapter
import com.kerite.pokedex.ui.activity.PokeDexDetailsActivity
import com.kerite.pokedex.ui.customview.BackPressedSearchView
import com.kerite.pokedex.ui.dialog.PokeDexFilterBottomDialogFragment
import com.kerite.pokedex.viewmodel.MainActivityViewModel
import com.kerite.pokedex.viewmodel.PokemonDexListAndFilterViewModel
import com.kerite.pokedex.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PokemonListFragment : BaseFragment<FragmentPokemonDexBinding>(
    FragmentPokemonDexBinding::inflate
), MenuProvider {
    private lateinit var pokemonDexListAndFilterViewModel: PokemonDexListAndFilterViewModel
    private val activityViewModel: SearchViewModel by activityViewModels()
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private val antiShaker = AntiShaker()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.apply {
            // 设置浮动按钮的点击事件
            pokemonDexFilterFab.setOnClickListener {
                PokeDexFilterBottomDialogFragment().show(
                    requireActivity().supportFragmentManager,
                    PokeDexFilterBottomDialogFragment::class.java.name
                )
            }
            mainActivityViewModel.bottomOffset.observe(viewLifecycleOwner) { offset ->
                pokemonDexFilterFab.translationY = offset
            }
        }
    }

    private fun setupRecyclerView() {
        binding.pokemonDexList.apply {
            pokemonDexListAndFilterViewModel =
                ViewModelProvider(requireActivity())[PokemonDexListAndFilterViewModel::class.java]
            val adapter =
                PokemonDexRecyclerAdapter(PokemonDexRecyclerAdapter.OnClickListener { dexNumber, regionalVariant ->
                    antiShaker.antiShake {
                        startActivity<PokeDexDetailsActivity>(
                            PokeDexDetailsActivity.INTENT_DEX_NUMBER to dexNumber
                        )
                    }
                }, this.context)
            layoutManager = LinearLayoutManager(this.context)
            this.adapter = adapter

            lifecycleScope.launch {
                pokemonDexListAndFilterViewModel.pokemonList.collectLatest {
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
