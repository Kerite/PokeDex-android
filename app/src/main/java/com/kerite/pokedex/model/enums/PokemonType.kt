package com.kerite.pokedex.model.enums

enum class PokemonType(
    val typeName: String,
    val typeTag: String,
    val color: String
) {
    NORMAL("一般", "normal", "#9199a1"),
    FLYING("飞行", "flying", "#8ea8de"),
    FIRE("火", "fire", "#fe9c54"),
    PSYCHIC("超能力", "psychic", "#f97178"),
    WATER("水", "water", "#4f90d5"),
    BUG("虫", "bug", "#91c02e"),
    ELECTRIC("电", "electric", "#f4d23c"),
    ROCK("岩石", "rock", "#c5b78b"),
    GRASS("草", "grass", "#62bb5a"),
    GHOST("幽灵", "ghost", "#5269ac"),
    ICE("冰", "ice", "#73cebf"),
    DRAGON("龙", "dragon", "#0a6dc2"),
    FIGHT("格斗", "fight", "#cd406a"),
    DARK("恶", "dark", "#5a5365"),
    POISON("毒", "poison", "#a96ac8"),
    STEEL("钢", "steel", "#5a8da1"),
    GROUND("地面", "ground", "#d87844"),
    FAIRY("妖精", "fairy", "#eb8fe6");
}