package com.ddd.sikdorok.core_ui.util

import android.content.Context
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun DialogFragment.show(fragmentManager: FragmentManager) {
    show(fragmentManager, this::class.java.simpleName)
}

fun Context.makeAlertDialog(
    title: String,
    confirmText: String = "네",
    cancelText: String = "아니오",
    onConfirm: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null
) {
    AlertDialog.Builder(
        this,
        com.ddd.sikdorok.core_design.R.style.AppAlertDialogTheme
    )
        .setMessage(title)
        .setPositiveButton(confirmText) { dialog, which ->
            onConfirm?.invoke()
        }
        .setNegativeButton(cancelText) { dialog, which ->
            onCancel?.invoke()
        }
        .show().apply {
            findViewById<TextView>(android.R.id.message)?.apply {
                textSize = 20f
                typeface = ResourcesCompat.getFont(
                    context,
                    com.ddd.sikdorok.core_design.R.font.leeseoyun
                )
                setTextColor(context.getColor(com.ddd.sikdorok.core_design.R.color.text_color))
            }
        }
}