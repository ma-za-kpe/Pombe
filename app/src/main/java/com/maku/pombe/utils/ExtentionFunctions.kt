package com.maku.pombe.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.IOException

// extension function to get bitmap from assets
fun Context.assetsToBitmap(fileName:String): Bitmap?{
    return try {
        val stream = assets.open(fileName)
        BitmapFactory.decodeStream(stream)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

// extension function to decode base64 string to bitmap
fun String.toBitmap():Bitmap?{
    Base64.decode(this, Base64.DEFAULT).apply {
        return BitmapFactory.decodeByteArray(this, 0, size)
    }
}