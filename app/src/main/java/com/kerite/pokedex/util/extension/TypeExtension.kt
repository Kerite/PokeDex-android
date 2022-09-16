package com.kerite.pokedex.util.extension

fun <T> Array<T>.toLinkedHashSet(): LinkedHashSet<T> =
    LinkedHashSet<T>().apply {
        addAll(this)
    }

fun IntRange.toLinkedHashSet(): LinkedHashSet<Int> =
    LinkedHashSet<Int>().apply {
        addAll(this.toHashSet())
    }

fun IntRange.toTypedArray(): Array<Int> = this.toList().toTypedArray()