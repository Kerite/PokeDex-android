package com.kerite.pokedex.util.extension

import android.content.Context
import android.provider.Settings
import android.widget.Toast

val Context.animatorDurationScale: Float
    get() = Settings.Global.getFloat(
        this.contentResolver,
        Settings.Global.ANIMATOR_DURATION_SCALE,
        1f
    )

fun Context.shortToast(text: String) =
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Context.longToast(text: String) =
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
