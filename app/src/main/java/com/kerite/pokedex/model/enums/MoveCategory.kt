package com.kerite.pokedex.model.enums

import androidx.annotation.ColorRes
import com.kerite.pokedex.R

enum class MoveCategory(
    val categoryName: String,
    @ColorRes val categoryColorRes: Int,
) {
    PHYSICAL("物理", R.color.move_category_physical),
    SPECIAL("特殊", R.color.move_category_special),
    STATUS("变化", R.color.move_category_status),
    MAX("极巨", R.color.move_category_max),
    GIGANTAMAX("超极巨", R.color.move_category_gigantamax),
    Z_MOVE("Z-招式", R.color.move_category_z_move)
}