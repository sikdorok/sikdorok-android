package com.ddd.sikdorok.navigator

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import com.ddd.sikdorok.navigator.signin.SignInNavigator
import com.ddd.sikdorok.signin.SignInActivity
import javax.inject.Inject

internal class SignInNavigatorImpl @Inject constructor() : SignInNavigator {
    override fun start(context: Context): Intent {
        return Intent(context, SignInActivity::class.java)
    }

    override fun start(context: Context, isFromReset: Boolean): Intent {
        return Intent(context, SignInActivity::class.java).apply {
            putExtras(
                bundleOf("isFromReset" to isFromReset)
            )
        }
    }
}
