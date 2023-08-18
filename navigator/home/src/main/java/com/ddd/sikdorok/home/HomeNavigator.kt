package com.ddd.sikdorok.home

import android.content.Context
import android.content.Intent
import com.ddd.sikdorok.navigator.core.Navigator

interface HomeNavigator : Navigator {
    fun start(context: Context, deeplink: String? = null): Intent
}
