package com.kerite.pokedex.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kerite.pokedex.MSP_WIDTH
import com.kerite.pokedex.PokedexApplication
import com.kerite.pokedex.R
import com.kerite.pokedex.databinding.ItemPokemonDexIndexBinding
import com.kerite.pokedex.entity.PokemonEntity
import com.kerite.pokedex.util.getBitmapFromAsset

class PokemonDexRecyclerAdapter() :
    ListAdapter<PokemonEntity, PokemonDexRecyclerAdapter.PokedexViewHolder>(diffCallback) {

    class PokedexViewHolder(
        private val binding: ItemPokemonDexIndexBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemonDex: PokemonEntity) {
            val context = binding.root.context
//            Log.e("ViewHolder", "Binding${pokemonDex}")
            binding.apply {
                pokemonName.text = pokemonDex.name
                pokemonType1.setImageBitmap(PokedexApplication.getTypeImage(pokemonDex.type1))
                val path =
                    "small_icon/${pokemonDex.iconRowIndex * MSP_WIDTH + pokemonDex.iconColumnIndex}.png"
                val bitmap = getBitmapFromAsset(context, path)
                pokemonHeader.setImageBitmap(bitmap)
                dexNumber.text = "#${pokemonDex.dexNumber}"
                pokemonSubName.text = pokemonDex.subName
                pokemonDexGeneration.text = context.resources.getString(
                    R.string.generation_simple,
                    pokemonDex.generation
                )
            }

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