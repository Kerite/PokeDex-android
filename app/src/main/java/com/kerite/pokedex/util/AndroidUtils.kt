package com.kerite.pokedex.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat

fun makeStatusNotification(message: String, context: Context) {
    val builder = NotificationCompat.Builder(context, "")
}

fun getDrawableId(context: Context, imageName: String): Int {
    try {
        val imageId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
        return imageId
    } catch (e: java.lang.Exception) {
        return 0
    }
}

fun getBitmapFromAsset(context: Context, filePath: String): Bitmap? {
    val assetManager = context.assets
    return try {
        BitmapFactory.decodeStream(assetManager.open(filePath))
    } catch (e: Exception) {
        null
    }
}