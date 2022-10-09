package com.kerite.pokedex.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import androidx.recyclerview.widget.GridLayoutManager
import com.kerite.fission.android.ui.SimpleListRecyclerAdapter
import com.kerite.pokedex.R
import com.kerite.pokedex.databinding.ItemDamageMultiplierBinding
import com.kerite.pokedex.databinding.WidgetDamageMultiplierBinding
import com.kerite.pokedex.model.enums.PokemonType
import com.kerite.pokedex.util.PokemonDamageUtils
import timber.log.Timber

class PokemonDamageMultiplierWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding: WidgetDamageMultiplierBinding
    var pokemonTypes = 0
        set(value) {
            field = value
            val pokemonTypeList = mutableListOf<PokemonType>()
            for (pokemonType in PokemonType.values) {
                if ((pokemonType.flag and field) != 0)
                    pokemonTypeList += pokemonType
            }
            Timber.tag("DamageMultiplier").d(pokemonTypeList.joinToString())
            val weakness = PokemonDamageUtils.getWeaknessAsDefender(*pokemonTypeList.toTypedArray())
            Timber.tag("DamageMultiplier").d("Weakness $weakness")
            val normal = PokemonDamageUtils.getNormalAsDefender(*pokemonTypeList.toTypedArray())
            Timber.tag("DamageMultiplier").d("Normal $normal")
            val superEffective = PokemonDamageUtils.getEffectiveAsDefender(*pokemonTypeList.toTypedArray())
            mWeakDamageAdapter.submitList(weakness)
            mNormalDamageAdapter.submitList(normal)
            mSuperDamageAdapter.submitList(superEffective)
        }
    private val mWeakDamageAdapter = SimpleListRecyclerAdapter<ItemDamageMultiplierBinding, Pair<PokemonType, Float>>(
        context, ItemDamageMultiplierBinding::inflate,
        onBind = {
            typeName.type = it.first
            damageMultiplier.text = "x${it.second}"
        }
    ).apply {
        animateEnabled = false
    }
    private val mSuperDamageAdapter = SimpleListRecyclerAdapter<ItemDamageMultiplierBinding, Pair<PokemonType, Float>>(
        context, ItemDamageMultiplierBinding::inflate,
        onBind = {
            typeName.type = it.first
            damageMultiplier.text = "x${it.second}"
        }
    ).apply {
        animateEnabled = false
    }
    private val mNormalDamageAdapter = SimpleListRecyclerAdapter<ItemDamageMultiplierBinding, Pair<PokemonType, Float>>(
        context, ItemDamageMultiplierBinding::inflate,
        onBind = {
            typeName.type = it.first
            damageMultiplier.text = "x${it.second}"
        }
    ).apply {
        animateEnabled = false
    }

    init {
        context.obtainStyledAttributes(
            attrs, R.styleable.PokemonDamageMultiplierWidget, defStyleAttr, 0
        ).apply {
            binding = WidgetDamageMultiplierBinding.inflate(
                LayoutInflater.from(context), this@PokemonDamageMultiplierWidget, true
            )
            pokemonTypes = if (isInEditMode) {
                PokemonType.WATER.flag or PokemonType.FIRE.flag
            } else {
                getInteger(R.styleable.PokemonDamageMultiplierWidget_pokemonTypes, 0)
            }
            recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.damageWeakMultiplierRecycler.apply {
            val mLayoutManager = GridLayoutManager(context, 2)
            mLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = 1
            }
            layoutManager = mLayoutManager
            adapter = mWeakDamageAdapter
            isNestedScrollingEnabled = false
            overScrollMode = OVER_SCROLL_NEVER
        }
        binding.damageSuperMultiplierRecycler.apply {
            val mLayoutManager = GridLayoutManager(context, 2)
            mLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = 1
            }
            layoutManager = mLayoutManager
            adapter = mSuperDamageAdapter
            isNestedScrollingEnabled = false
            overScrollMode = OVER_SCROLL_NEVER
        }
        binding.damageNormalMultiplierRecycler.apply {
            val mLayoutManager = GridLayoutManager(context, 2)
            mLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = 1
            }
            layoutManager = mLayoutManager
            adapter = mNormalDamageAdapter
            isNestedScrollingEnabled = false
            overScrollMode = OVER_SCROLL_NEVER
        }
    }

    fun addPokemonTypeFlag(pokemonType: PokemonType) {
        pokemonTypes = pokemonTypes or pokemonType.flag
    }

    fun clearPokemonTypeFlag() {
        pokemonTypes = 0
    }
}