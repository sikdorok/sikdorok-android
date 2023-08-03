package com.ddd.sikdorok.signup

import android.content.Context
import android.content.Intent
import com.ddd.sikdorok.navigator.core.Navigator

interface SignUpNavigator : Navigator {
    fun start(context: Context, payload: Any?): Intent
}
