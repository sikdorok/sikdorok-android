package com.ddd.sikdorok.send_password

import android.content.Context
import android.content.Intent
import com.ddd.sikdorok.navigator.core.Navigator

interface SendPasswordNavigator : Navigator {
    fun start(context: Context, payload: Any?): Intent
}
