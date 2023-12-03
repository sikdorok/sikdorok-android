package com.ddd.sikdorok.navigator

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import com.ddd.sikdorok.signup.SignUpActivity
import com.ddd.sikdorok.signup.SignUpNavigator
import javax.inject.Inject

internal class SignUpNavigatorImpl @Inject constructor(): SignUpNavigator {

    override fun start(context: Context): Intent {
        return Intent(context, SignUpActivity::class.java)
    }

    override fun start(context: Context, payload: Any?): Intent {
        return Intent(context, SignUpActivity::class.java).apply {
            putExtras(bundleOf(
                PAYLOAD to payload
            ))
        }
    }

    companion object {
        private const val PAYLOAD = "payload"
    }
}
