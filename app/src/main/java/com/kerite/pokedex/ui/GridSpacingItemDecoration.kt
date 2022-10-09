package com.kerite.pokedex.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class GridSpacingItemDecoration(
    private val mSpanCount: Int,
    private val mRowSpacing: Int,
    private val mColumnSpacing: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // 获取view 在adapter中的位置。

        val column = position % mSpanCount // view 所在的列

        outRect.left = column * mColumnSpacing / mSpanCount // column * (列间距 * (1f / 列数))

        outRect.right =
            mColumnSpacing - (column + 1) * mColumnSpacing / mSpanCount // 列间距 - (column + 1) * (列间距 * (1f /列数))

        // 如果position > 行数，说明不是在第一行，则不指定行高，其他行的上间距为 top=mRowSpacing

        // 如果position > 行数，说明不是在第一行，则不指定行高，其他行的上间距为 top=mRowSpacing
        if (position >= mSpanCount) {
            outRect.top = mRowSpacing // item top
        }
    }
}