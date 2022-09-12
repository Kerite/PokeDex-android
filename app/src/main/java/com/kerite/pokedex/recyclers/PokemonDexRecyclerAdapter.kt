package com.kerite.pokedex.recyclers

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kerite.pokedex.PokedexApplication
import com.kerite.pokedex.databinding.ItemPokemonDexIndexBinding
import com.kerite.pokedex.model.PokemonDex
import com.kerite.pokedex.viewmodel.PokemonDexListViewModel

class PokemonDexRecyclerAdapter(
    private val dataSet: List<PokemonDexListViewModel>
) : RecyclerView.Adapter<PokemonDexRecyclerAdapter.PokedexViewHolder>() {

    class PokedexViewHolder(
        private val binding: ItemPokemonDexIndexBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(pokemonDex: PokemonDexListViewModel) {
            Log.e("ViewHolder", "Binding${pokemonDex}")
            binding.pokemonName.text = pokemonDex.name
            binding.pokemonType1.setImageBitmap(PokedexApplication.getTypeImage(pokemonDex.type1))
            if (pokemonDex.type2 != null) {
                binding.pokemonType2.setImageBitmap(PokedexApplication.getTypeImage(pokemonDex.type2))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokedexViewHolder {
        val itemBinding =
            ItemPokemonDexIndexBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokedexViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PokedexViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size
}