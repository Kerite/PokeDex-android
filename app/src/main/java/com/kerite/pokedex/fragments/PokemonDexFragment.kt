package com.kerite.pokedex.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kerite.pokedex.databinding.FragmentPokemonDexBinding
import com.kerite.pokedex.model.enums.PokemonType
import com.kerite.pokedex.recyclers.PokemonDexRecyclerAdapter
import com.kerite.pokedex.viewmodel.PokemonDexListViewModel
import java.util.logging.Logger

/**
 * A simple [Fragment] subclass.
 * Use the [PokemonDexFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PokemonDexFragment : Fragment() {
    private lateinit var binding: FragmentPokemonDexBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonDexBinding.inflate(layoutInflater)

        val pokemonDexRecyclerView = binding.pokemonDexList
        val testData = getTestData()
        pokemonDexRecyclerView.layoutManager = LinearLayoutManager(this.context)
        pokemonDexRecyclerView.adapter = PokemonDexRecyclerAdapter(testData)

        return binding.root
    }

    private fun getTestData(): List<PokemonDexListViewModel> {
        return arrayListOf(
            PokemonDexListViewModel(1, "Ok", PokemonType.STEEL),
            PokemonDexListViewModel(1, "Ok", PokemonType.BUG, PokemonType.DARK),
            PokemonDexListViewModel(1, "Ok", PokemonType.BUG),
            PokemonDexListViewModel(1, "Ok", PokemonType.BUG)
        )
    }
}