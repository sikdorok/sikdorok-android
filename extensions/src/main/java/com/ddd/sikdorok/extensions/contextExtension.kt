package com.ddd.sikdorok.extensions

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat
import androidx.core.content.contentValuesOf
import androidx.core.os.bundleOf
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream

private val metrics by lazy { Resources.getSystem().displayMetrics }

fun Context.showSnackBar(
    view: View,
    message: String,
    @ColorRes backgroundColor: Int,
    @ColorRes textColor: Int,
    duration: Int
) = Snackbar.make(view, message, duration)
    .setBackgroundTint(ContextCompat.getColor(this, backgroundColor))
    .setTextColor(ContextCompat.getColor(this, textColor))
    .show()

val Int.dpToPx: Int
    get() {
        return (metrics.density * this).toInt()
    }

fun Context.compressBitmap(
    format: CompressFormat,
    bitmap: Bitmap?,
    @IntRange(from = 0, to = 100) quality: Int
): Uri? {

    val contentValues = contentValuesOf(
        MediaStore.MediaColumns.DISPLAY_NAME to "${System.currentTimeMillis()}.png",
        MediaStore.MediaColumns.MIME_TYPE to "image/png",
        MediaStore.MediaColumns.RELATIVE_PATH to Environment.DIRECTORY_PICTURES,
    )

    val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    contentResolver.also { resolver ->
        imageUri?.let { uri ->
            resolver.openOutputStream(uri).use { stream ->
                bitmap?.compress(format, quality, stream)
                stream?.flush()
            }
        }
    }

    return imageUri
}
