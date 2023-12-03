package com.ddd.sikdorok.core_ui.util

import android.content.Context
import android.graphics.Paint
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

@BindingAdapter("bind:underline")
fun AppCompatTextView.setTextUnderline(isEnabled: Boolean = true) {
    paintFlags = paintFlags.or(Paint.UNDERLINE_TEXT_FLAG)
}