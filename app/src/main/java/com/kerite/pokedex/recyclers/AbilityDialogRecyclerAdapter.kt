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
import com.kerite.pokedex.databinding.ItemAbilityDialogBinding

class AbilityDialogRecyclerAdapter(
    val context: Context,
    private val onClick: (PokemonDetailsEntity) -> Unit
) : ListAdapter<PokemonDetailsEntity, AbilityDialogRecyclerAdapter.AbilityDialogRecyclerViewHolder>(diffCallback) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<PokemonDetailsEntity>() {
            override fun areItemsTheSame(oldItem: PokemonDetailsEntity, newItem: PokemonDetailsEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PokemonDetailsEntity, newItem: PokemonDetailsEntity): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbilityDialogRecyclerViewHolder {
        val viewBinding = ItemAbilityDialogBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return AbilityDialogRecyclerViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: AbilityDialogRecyclerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClick)
    }

    class AbilityDialogRecyclerViewHolder(
        private val binding: ItemAbilityDialogBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(details: PokemonDetailsEntity, clickListener: (PokemonDetailsEntity) -> Unit) =
            binding.apply {
                root.setOnClickListener {
                    clickListener(details)
                }
                val headerImagePath = "file:///android_asset/images/${details.dexNumber}#${details.name}#${details.formName ?: ""}#.webp"
                    .replace("##", "#")
                val headerUri = Uri.parse(headerImagePath)
                Glide.with(binding.root)
                    .load(headerUri)
                    .into(pokemonImage)
//                pokemonImage.load(headerUri)
//                val file = headerUri.toFile()
//                Timber.tag("LoadImage").d("$headerImagePath ${file.exists()}")
                pokemonName.text = details.name
                pokemonFormName.text = details.formName
            }
    }
}