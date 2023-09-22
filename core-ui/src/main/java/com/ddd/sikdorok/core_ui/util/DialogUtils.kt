package com.ddd.sikdorok.core_ui.util

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun DialogFragment.show(fragmentManager: FragmentManager) {
    show(fragmentManager, this::class.java.simpleName)
}