package com.ddd.sikdorok.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

fun uriToBitmap(context: Context, uri: Uri?): Bitmap? {
    var inputStream: InputStream? = null
    try {
        // URI로부터 InputStream을 열어 비트맵으로 디코딩합니다.
        inputStream = context.contentResolver.openInputStream(uri!!)
        if (inputStream != null) {
            return BitmapFactory.decodeStream(inputStream)
        }
    } catch (e: FileNotFoundException) {
        Log.e("Error", "File not found: ${e.message}")
    } catch (e: IOException) {
        Log.e("Error", "Error accessing file: ${e.message}")
    } finally {
        // 사용이 완료된 InputStream을 닫습니다.
        inputStream?.close()
    }
    return null
}

fun convertImageBitmapToByteArray(imageBitmap: Bitmap?): ByteArray {
    val stream = ByteArrayOutputStream()
    return try {
        stream.use {
            imageBitmap?.compress(
                Bitmap.CompressFormat.JPEG,
                100,
                stream
            )
        }
        stream.toByteArray()
    } catch (e: Exception) {
        stream.toByteArray()
    }
}