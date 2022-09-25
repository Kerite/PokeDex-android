package com.kerite.pokedex.recyclers

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kerite.pokedex.MSP_WIDTH
import com.kerite.pokedex.R
import com.kerite.pokedex.database.entity.PokemonEntity
import com.kerite.pokedex.databinding.ItemPokemonDexIndexBinding
import com.kerite.pokedex.model.enums.PokemonRegionalVariant

class PokemonDexRecyclerAdapter(
    private val onClickListener: OnClickListener,
    val context: Context
) : ListAdapter<PokemonEntity, PokemonDexRecyclerAdapter.PokedexViewHolder>(diffCallback) {
    class PokedexViewHolder(
        private val binding: ItemPokemonDexIndexBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemonDex: PokemonEntity, onClick: (Int, PokemonRegionalVariant) -> Unit) {
            val context = binding.root.context
//            Log.e("ViewHolder", "Binding${pokemonDex}")
            binding.apply {
                pokemonName.text = pokemonDex.name
                pokemonType1.type = pokemonDex.type1
                val path =
                    "file:///android_asset/small_icon/${pokemonDex.iconRowIndex * MSP_WIDTH + pokemonDex.iconColumnIndex}.png"
                pokemonHeader.load(Uri.parse(path))
                dexNumber.text = "#${pokemonDex.dexNumber}"
                pokemonSubName.text = pokemonDex.subName.displayedName
                pokemonDexGeneration.text = context.resources.getString(
                    R.string.generation_simple,
                    pokemonDex.generation
                )
                if (pokemonDex.type2 != null) {
                    pokemonType2.visibility = View.VISIBLE
                    pokemonType2.type = pokemonDex.type2
                } else {
                    pokemonType2.visibility = View.INVISIBLE
                }
                root.setOnClickListener {
                    onClick(pokemonDex.dexNumber, pokemonDex.subName)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokedexViewHolder {
        val itemBinding =
            ItemPokemonDexIndexBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokedexViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PokedexViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item) { dexNumber, regionalVariant ->
            onClickListener.onClick(dexNumber, regionalVariant)
        }
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

    class OnClickListener(val clickListener: (Int, PokemonRegionalVariant) -> Unit) {
        fun onClick(dexNumber: Int, regionalVariant: PokemonRegionalVariant) =
            clickListener(dexNumber, regionalVariant)
    }
}