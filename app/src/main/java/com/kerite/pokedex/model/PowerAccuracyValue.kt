package com.kerite.pokedex.model

import android.content.Context
import com.kerite.pokedex.R

sealed class PowerAccuracyValue {
    data class IntValue(val value: Int) : PowerAccuracyValue() {
        override fun getDisplayedText(context: Context): String {
            return value.toString()
        }
    }

    object Variably : PowerAccuracyValue() {
        override fun getDisplayedText(context: Context): String {
            return context.getString(R.string.variably)
        }
    }

    object NoValue : PowerAccuracyValue() {
        override fun getDisplayedText(context: Context): String {
            return "—"
        }
    }

    abstract fun getDisplayedText(context: Context): String
}
