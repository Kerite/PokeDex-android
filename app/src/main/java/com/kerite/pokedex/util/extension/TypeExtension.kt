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

fun String?.isNumeric(): Boolean {
    if (this == null || this.isEmpty()) return false
    for (x in 0 until this.length) {
        val c: Char = this[x]
        if (x == 0 && c == '-') continue  // negative
        if (c in '0'..'9') continue  // 0 - 9
        return false // invalid
    }
    return true // valid
}