package com.kerite.pokedex.recyclers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kerite.pokedex.MSP_WIDTH
import com.kerite.pokedex.PokedexApplication
import com.kerite.pokedex.databinding.ItemPokemonDexIndexBinding
import com.kerite.pokedex.entity.PokemonEntity
import com.kerite.pokedex.utils.getBitmapFromAsset
import com.kerite.pokedex.utils.getDrawableId
import com.kerite.pokedex.viewmodel.PokemonDexListViewModel

class PokemonDexRecyclerAdapter() :
    ListAdapter<PokemonEntity, PokemonDexRecyclerAdapter.PokedexViewHolder>(diffCallback) {

    class PokedexViewHolder(
        private val binding: ItemPokemonDexIndexBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemonDex: PokemonEntity) {
//            Log.e("ViewHolder", "Binding${pokemonDex}")
            binding.pokemonName.text = pokemonDex.name + "  " + (pokemonDex.subName ?: "")
            binding.pokemonType1.setImageBitmap(PokedexApplication.getTypeImage(pokemonDex.type1))
            val path =
                "small_icon/${pokemonDex.iconRowIndex * MSP_WIDTH + pokemonDex.iconColumnIndex}.png"
            val bitmap = getBitmapFromAsset(binding.root.context, path)
            binding.pokemonHeader.setImageBitmap(bitmap)

            if (pokemonDex.type2 != null) {
                binding.pokemonType2.visibility = View.VISIBLE
                binding.pokemonType2.setImageBitmap(PokedexApplication.getTypeImage(pokemonDex.type2))
            } else {
                binding.pokemonType2.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokedexViewHolder {
        val itemBinding =
            ItemPokemonDexIndexBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokedexViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PokedexViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<PokemonEntity>() {
            override fun areItemsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PokemonEntity,
                newItem: PokemonEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}