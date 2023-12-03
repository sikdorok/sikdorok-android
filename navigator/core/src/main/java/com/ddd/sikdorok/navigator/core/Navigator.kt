package com.ddd.sikdorok.navigator.core

import android.content.Context
import android.content.Intent

interface Navigator {
    fun start(context: Context): Intent
}
