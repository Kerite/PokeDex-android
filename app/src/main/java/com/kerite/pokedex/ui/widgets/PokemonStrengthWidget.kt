package com.kerite.pokedex.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.kerite.pokedex.R
import com.kerite.pokedex.databinding.WidgetSpeciesStrengthBinding

class PokemonStrengthWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    var hp = 0
        set(value) {
            field = value
            binding.apply {
                strengthHp.progress = value
                strengthValueHp.text = value.toString()
                strengthValueTotal.text = mTotal.toString()
            }
        }
    var attack = 0
        set(value) {
            field = value
            binding.apply {
                strengthAttack.progress = value
                strengthValueAttack.text = value.toString()
                strengthValueTotal.text = mTotal.toString()
            }
        }
    var defence = 0
        set(value) {
            field = value
            binding.apply {
                strengthDefence.progress = value
                strengthValueDefence.text = value.toString()
                strengthValueTotal.text = mTotal.toString()
            }
        }
    var specialAttack = 0
        set(value) {
            field = value
            binding.apply {
                strengthSpecialAttack.progress = value
                strengthValueSpecialAttack.text = value.toString()
                strengthValueTotal.text = mTotal.toString()
            }
        }
    var specialDefence = 0
        set(value) {
            field = value
            binding.apply {
                strengthSpecialDefence.progress = value
                strengthValueSpecialDefence.text = value.toString()
                strengthValueTotal.text = mTotal.toString()
            }
        }
    var speed = 0
        set(value) {
            field = value
            binding.apply {
                strengthSpeed.progress = value
                strengthValueSpeed.text = value.toString()
                strengthValueTotal.text = mTotal.toString()
            }
        }
    private val mTotal
        get() = hp + attack + defence + specialAttack + specialDefence + speed
    private val binding: WidgetSpeciesStrengthBinding

    init {
        context.obtainStyledAttributes(
            attrs, R.styleable.PokemonStrengthWidget
        ).apply {
//            val view = LayoutInflater.from(context)
//                .inflate(R.layout.widget_species_strength, this@PokemonStrengthWidget)
//            binding = WidgetSpeciesStrengthBinding.bind(view)
            binding = WidgetSpeciesStrengthBinding.inflate(
                LayoutInflater.from(context), this@PokemonStrengthWidget, true
            )

            hp = getInt(R.styleable.PokemonStrengthWidget_hp, 0)
            attack = getInt(R.styleable.PokemonStrengthWidget_attack, 0)
            defence = getInt(R.styleable.PokemonStrengthWidget_defence, 0)
            specialAttack = getInt(R.styleable.PokemonStrengthWidget_specialAttack, 0)
            specialDefence = getInt(R.styleable.PokemonStrengthWidget_specialDefence, 0)
            speed = getInt(R.styleable.PokemonStrengthWidget_speed, 0)
            recycle()
        }
    }
}