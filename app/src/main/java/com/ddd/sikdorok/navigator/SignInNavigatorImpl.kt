package com.ddd.sikdorok.navigator

import android.content.Context
import android.content.Intent
import com.ddd.sikdorok.navigator.signin.SignInNavigator
import com.ddd.sikdorok.signin.SignInActivity
import javax.inject.Inject

internal class SignInNavigatorImpl @Inject constructor(): SignInNavigator {
    override fun start(context: Context): Intent {
        return Intent(context, SignInActivity::class.java)
    }
}
