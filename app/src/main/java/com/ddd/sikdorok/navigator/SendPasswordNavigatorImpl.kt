package com.ddd.sikdorok.navigator

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import com.ddd.sikdorok.send_password.SendPasswordActivity
import com.ddd.sikdorok.send_password.SendPasswordNavigator
import javax.inject.Inject

class SendPasswordNavigatorImpl @Inject constructor(): SendPasswordNavigator {

    override fun start(context: Context): Intent {
        return Intent(context, SendPasswordActivity::class.java)
    }

    override fun start(context: Context, payload: Any?): Intent {
        return Intent(context, SendPasswordActivity::class.java).apply {
            putExtras(bundleOf("email" to payload))
        }
    }
}
