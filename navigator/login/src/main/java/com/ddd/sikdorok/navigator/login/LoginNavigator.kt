package com.ddd.sikdorok.navigator.login

import android.content.Context
import android.content.Intent
import com.ddd.sikdorok.navigator.core.Navigator

interface LoginNavigator : Navigator {
    fun start(context: Context, isFromDelete: Boolean = false): Intent
}
