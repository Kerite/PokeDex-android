package com.kerite.pokedex.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MenuItem.OnActionExpandListener
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.appcompat.widget.SearchView
import androidx.core.net.toUri
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.kerite.fission.android.extensions.startActivity
import com.kerite.fission.android.ui.BaseFragment
import com.kerite.fission.android.ui.SimpleListRecyclerAdapter
import com.kerite.pokedex.MSP_WIDTH
import com.kerite.pokedex.R
import com.kerite.pokedex.database.entity.PokemonEntity
import com.kerite.pokedex.databinding.FragmentPokemonDexBinding
import com.kerite.pokedex.databinding.ItemPokemonDexIndexBinding
import com.kerite.pokedex.ui.activity.PokeDexDetailsActivity
import com.kerite.pokedex.ui.customview.BackPressedSearchView
import com.kerite.pokedex.ui.dialog.PokeDexFilterBottomDialogFragment
import com.kerite.pokedex.viewmodel.MainActivityViewModel
import com.kerite.pokedex.viewmodel.PokemonDexListAndFilterViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PokemonListFragment : BaseFragment<FragmentPokemonDexBinding>(
    FragmentPokemonDexBinding::inflate
), MenuProvider {
    private val pokemonDexListAndFilterViewModel: PokemonDexListAndFilterViewModel by activityViewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var recyclerAdapter: SimpleListRecyclerAdapter<ItemPokemonDexIndexBinding, PokemonEntity>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        setupRecyclerView()
        lifecycleScope.launch {
            pokemonDexListAndFilterViewModel.pokemonList.collectLatest {
                recyclerAdapter.submitList(it)
                binding.pokemonDexListRecyclerView.visibility = View.VISIBLE
                binding.pokemonDexCircularProgress.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupRecyclerView() {
        // <editor-fold defaultstate="collapsed" desc="设置RecyclerView">
        binding.pokemonDexListRecyclerView.apply {
//            isNestedScrollingEnabled = false
            recyclerAdapter = SimpleListRecyclerAdapter(
                context, ItemPokemonDexIndexBinding::inflate,
                onBind = {
                    pokemonName.text = it.name
                    pokemonType1.type = it.type1
                    dexNumber.text = resources.getString(R.string.dex_number_format, it.dexNumber)
                    pokemonSubName.text = it.subName.displayedName
                    pokemonDexGeneration.text =
                        resources.getString(R.string.generation_simple, it.generation)
                    if (it.type2 == null) {
                        pokemonType2.visibility = View.INVISIBLE
                    } else {
                        pokemonType2.visibility = View.VISIBLE
                        pokemonType2.type = it.type2
                    }
                    val path =
                        "file:///android_asset/small_icon/${it.iconRowIndex * MSP_WIDTH + it.iconColumnIndex}.png"
                    pokemonHeader.load(path.toUri())
                },
                onItemClick = {
                    startActivity<PokeDexDetailsActivity>(
                        PokeDexDetailsActivity.INTENT_DEX_NUMBER to it.dexNumber
                    )
                },
                animRes = R.anim.anim_recycler_item
            )
            layoutManager = LinearLayoutManager(this.context)
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
            val animationController = LayoutAnimationController(animation)
            animationController.order = LayoutAnimationController.ORDER_NORMAL
            animationController.delay = 0.2f
            itemAnimator
            this.adapter = recyclerAdapter
        }
        // </editor-fold>
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_search_filter, menu)

        val searchMenu = menu.findItem(R.id.action_search)
        searchMenu.setOnActionExpandListener(object : OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean = true
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean = true
        })
        (searchMenu.actionView as BackPressedSearchView).apply {
            queryHint = resources.getString(R.string.query_hint_search_pokemon)
            val mQuery = pokemonDexListAndFilterViewModel.searchWord.value
            if (mQuery.isNotBlank()) {
                onActionViewExpanded()
                setQuery(mQuery, false)
                isFocusable = true
            }
            setOnCloseListener { false }
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//        when (menuItem.itemId) {
//            R.id.action_search ->
//                pokemonDexListAndFilterViewModel.updateSearchWord("")
//        }
        return true
    }
}