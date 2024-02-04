package com.ddd.sikdorok.navigator

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import com.ddd.sikdorok.login.LoginActivity
import com.ddd.sikdorok.navigator.login.LoginNavigator
import javax.inject.Inject

internal class LoginNavigatorImpl @Inject constructor() : LoginNavigator {

    override fun start(context: Context): Intent {
        return Intent(context, LoginActivity::class.java)
    }

    override fun start(context: Context, isFromDelete: Boolean): Intent {
        return Intent(context, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            putExtras(
                bundleOf(
                    "isFromDelete" to isFromDelete
                )
            )
        }
    }
}
