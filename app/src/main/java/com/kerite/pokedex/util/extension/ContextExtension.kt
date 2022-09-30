package com.kerite.pokedex.util.extension

import android.content.Context
import android.provider.Settings

val Context.animatorDurationScale: Float
    get() = Settings.Global.getFloat(
        this.contentResolver,
        Settings.Global.ANIMATOR_DURATION_SCALE,
        1f
    )
