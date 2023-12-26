package com.ddd.sikdorok.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.IOException

@Throws(IOException::class)
fun uriToBitmap(context: Context, uri: Uri?): Bitmap? {
    return if (uri == null) {
        null
    } else {
        val resolver = context.contentResolver
        val inputStream = resolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    }
}

fun convertImageBitmapToByteArray(imageBitmap: Bitmap?): ByteArray {
    val stream = ByteArrayOutputStream()
    return try {
        stream.use {
            imageBitmap?.compress(
                Bitmap.CompressFormat.PNG,
                100,
                stream
            )
        }
        stream.toByteArray()
    } catch (e: Exception) {
        stream.toByteArray()
    }
}