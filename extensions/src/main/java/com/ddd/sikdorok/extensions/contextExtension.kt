package com.ddd.sikdorok.extensions

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

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
