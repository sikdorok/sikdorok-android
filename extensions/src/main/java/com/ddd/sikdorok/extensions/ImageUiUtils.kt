package com.ddd.sikdorok.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import com.google.firebase.crashlytics.FirebaseCrashlytics
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

fun uriToBitmap(context: Context, uri: Uri?): Bitmap? {
    var inputStream: InputStream? = null
    try {
        // URI로부터 InputStream을 열어 비트맵으로 디코딩합니다.
        inputStream = uri?.let { context.contentResolver.openInputStream(uri) }
        if (inputStream != null) {
            return BitmapFactory.decodeStream(inputStream)
        }
    } catch (e: Exception) {
        FirebaseCrashlytics.getInstance().recordException(e)
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

// 이미지 회전 정보 가져오기
fun getOrientationOfImage(context: Context, uri: Uri?): Int {
    // uri -> inputStream
    val inputStream = uri?.let { context.contentResolver.openInputStream(uri) }
    val exif: ExifInterface? = try {
        ExifInterface(inputStream!!)
    } catch (e: IOException) {
        e.printStackTrace()
        return -1
    }

    inputStream.close()

    // 회전된 각도 알아내기
    val orientation =
        exif?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
    if (orientation != -1) {
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> return 90
            ExifInterface.ORIENTATION_ROTATE_180 -> return 180
            ExifInterface.ORIENTATION_ROTATE_270 -> return 270
        }
    }
    return 0
}

fun getRotatedBitmap(bitmap: Bitmap?, degrees: Float): Bitmap? {
    if (bitmap == null) return null
    if (degrees == 0F) return bitmap
    val m = Matrix()
    m.setRotate(degrees, bitmap.width.toFloat() / 2, bitmap.height.toFloat() / 2)
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, m, true)
}
