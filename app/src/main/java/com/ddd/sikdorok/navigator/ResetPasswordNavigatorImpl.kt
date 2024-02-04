package com.ddd.sikdorok.navigator

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import com.ddd.sikdorok.reset_password.ResetPasswordActivity
import com.example.reset_password.ResetPasswordNavigator
import javax.inject.Inject

class ResetPasswordNavigatorImpl @Inject constructor() : ResetPasswordNavigator {
    override fun start(context: Context): Intent {
        return Intent(context, ResetPasswordActivity::class.java)
    }

    override fun start(context: Context, userId: String, code: String): Intent {
        return Intent(context, ResetPasswordActivity::class.java).apply {
            putExtras(
                bundleOf(
                    "userId" to userId,
                    "code" to code
                )
            )
        }
    }
}