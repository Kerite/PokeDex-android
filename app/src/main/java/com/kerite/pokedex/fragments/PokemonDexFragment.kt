package com.kerite.pokedex.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kerite.pokedex.databinding.FragmentPokemonDexBinding
import com.kerite.pokedex.model.enums.PokemonType
import com.kerite.pokedex.recyclers.PokemonDexRecyclerAdapter
import com.kerite.pokedex.viewmodel.MainActivityViewModel
import com.kerite.pokedex.viewmodel.PokemonDexListViewModel
import java.util.logging.Logger

/**
 * A simple [Fragment] subclass.
 * Use the [PokemonDexFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PokemonDexFragment : Fragment() {
    private lateinit var binding: FragmentPokemonDexBinding
    private lateinit var pokemonListViewModel: PokemonDexListViewModel
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonDexBinding.inflate(layoutInflater)

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        pokemonListViewModel =
            ViewModelProvider(requireActivity())[PokemonDexListViewModel::class.java]
        val adapter = PokemonDexRecyclerAdapter()
        val pokemonDexRecyclerView = binding.pokemonDexList
        pokemonDexRecyclerView.layoutManager = LinearLayoutManager(this.context)
        pokemonDexRecyclerView.adapter = adapter
        pokemonListViewModel.setFilter("")

        activityViewModel.searchFilter.observe(viewLifecycleOwner) { filter ->
            Log.d("Filter", "Filter Updated to $filter")
            pokemonListViewModel.setFilter(filter)
        }
        pokemonListViewModel.getPokemonList().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}