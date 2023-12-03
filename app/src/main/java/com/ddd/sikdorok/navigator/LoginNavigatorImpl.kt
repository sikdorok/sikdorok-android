package com.ddd.sikdorok.navigator

import android.content.Context
import android.content.Intent
import com.ddd.sikdorok.login.LoginActivity
import com.ddd.sikdorok.navigator.login.LoginNavigator
import javax.inject.Inject

internal class LoginNavigatorImpl @Inject constructor(): LoginNavigator {

    override fun start(context: Context): Intent {
        return Intent(context, LoginActivity::class.java)
    }

}
