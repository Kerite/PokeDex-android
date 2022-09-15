package com.kerite.pokedex.util

import android.content.res.Resources
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

@Suppress("UNCHECKED_CAST")
internal fun <T : ViewBinding> Any.inflateBinding(inflater: LayoutInflater): T {
    return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
        .filterIsInstance<Class<T>>()
        .first()
        .getDeclaredMethod("inflate", LayoutInflater::class.java)
        .also { it.isAccessible = true }
        .invoke(null, inflater) as T
}