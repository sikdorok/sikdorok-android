package com.ddd.sikdorok.navigator.signin

import android.content.Context
import android.content.Intent
import com.ddd.sikdorok.navigator.core.Navigator

interface SignInNavigator : Navigator {
    fun start(context: Context, isFromReset: Boolean) : Intent
}