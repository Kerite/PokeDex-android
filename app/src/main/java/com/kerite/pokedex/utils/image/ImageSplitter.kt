package com.kerite.pokedex.utils.image

import android.graphics.Bitmap

class ImageSplitter(
    private val bitmap: Bitmap,
    private val width: Int,
    private val height: Int
) {
    private val splitBlock: Array<Array<Bitmap?>?> = arrayOfNulls(width)

    fun process() {
        val fullWidth = bitmap.width
        val fullHeight = bitmap.height
        val pieceWidth = fullWidth / width
        val pieceHeight = fullHeight / height
        for (i in 0 until width) {
            val tempList = arrayOfNulls<Bitmap>(height)
            for (j in 0 until height) {
                val part = Bitmap.createBitmap(
                    bitmap,
                    j * pieceWidth,
                    i * pieceHeight,
                    pieceWidth,
                    pieceHeight
                )
                tempList.plus(part)
            }
            splitBlock[i] = tempList
        }
    }

    fun block(index: Int): Bitmap? {
        return try {
            splitBlock[index / width]?.get(index % width)
        } catch (e: IndexOutOfBoundsException) {
            return null
        }
    }
}
