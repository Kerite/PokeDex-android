package com.kerite.pokedex.model.enums

enum class MovePattern {
    LEVEL,
    TM_MACHINE,
    BREED,
    TEACH;

    companion object {
        fun fromIndex(index: Int): MovePattern {
            for (value in values()) {
                if (value.ordinal == index) {
                    return value
                }
            }
            throw IndexOutOfBoundsException()
        }
    }
}