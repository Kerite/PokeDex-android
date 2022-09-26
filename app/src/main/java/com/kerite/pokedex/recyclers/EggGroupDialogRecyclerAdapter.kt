package com.kerite.pokedex.recyclers

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kerite.pokedex.database.entity.PokemonDetailsEntity
import com.kerite.pokedex.databinding.ItemEggGroupDialogBinding

class EggGroupDialogRecyclerAdapter(
        private val context: Context,
        private val onClick: (PokemonDetailsEntity) -> Unit
) : ListAdapter<PokemonDetailsEntity, EggGroupDialogRecyclerAdapter.EggGroupItemViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<PokemonDetailsEntity>() {
            override fun areItemsTheSame(oldItem: PokemonDetailsEntity, newItem: PokemonDetailsEntity): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PokemonDetailsEntity, newItem: PokemonDetailsEntity): Boolean =
                    oldItem == newItem
        }
    }

    class EggGroupItemViewHolder(
            private val binding: ItemEggGroupDialogBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(details: PokemonDetailsEntity, onClick: (PokemonDetailsEntity) -> Unit) {
            binding.apply {
                pokemonNameTextView.text = details.name
                pokemonFormName.text = details.formName
                val headerImagePath = "file:///android_asset/images/${details.dexNumber}#${details.name}#${details.formName ?: ""}#.webp"
                        .replace("##", "#")
                val headerUri = Uri.parse(headerImagePath)
                Glide.with(root)
                        .load(headerUri)
                        .into(pokemonThumbnail)
                root.setOnClickListener {
                    onClick(details)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EggGroupItemViewHolder {
        return EggGroupItemViewHolder(
                ItemEggGroupDialogBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: EggGroupItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClick)
    }
}