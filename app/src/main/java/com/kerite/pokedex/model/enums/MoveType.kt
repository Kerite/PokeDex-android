package com.kerite.pokedex.model.enums

enum class MoveType(
    val moveName: String,
) {
    PHYSICAL("物理"),
    SPECIAL("特殊"),
    STATUS("变化"),
    MAX("极巨"),
    GIGANTAMAX("超极巨")
}