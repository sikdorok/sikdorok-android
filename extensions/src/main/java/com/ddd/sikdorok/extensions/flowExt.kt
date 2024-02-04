package com.ddd.sikdorok.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun EditText.textChanges() = callbackFlow {

    val listener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
            trySend(text)
        }

        override fun afterTextChanged(p0: Editable?) {}
    }

    addTextChangedListener(listener)

    awaitClose {
        removeTextChangedListener(listener)
    }
}
