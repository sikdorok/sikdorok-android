package com.ddd.sikdorok.navigator

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import com.ddd.sikdorok.signup.SignUpActivity
import com.ddd.sikdorok.signup.SignUpNavigator
import javax.inject.Inject

internal class SignUpNavigatorImpl @Inject constructor() : SignUpNavigator {

    override fun start(context: Context): Intent {
        return Intent(context, SignUpActivity::class.java)
    }

    override fun start(context: Context, payload: Any?): Intent {
        return Intent(context, SignUpActivity::class.java).apply {
            putExtras(
                bundleOf(
                    PAYLOAD to payload
                )
            )
        }
    }

    override fun start(
        context: Context,
        email: String?,
        oauthId: Long?,
        oauthType: String?
    ): Intent {
        return Intent(context, SignUpActivity::class.java).apply {
            putExtras(
                bundleOf(
                    PAYLOAD to email,
                    KEY_OAUTH_ID to oauthId,
                    KEY_OAUTH_TYPE to oauthType
                )
            )
        }
    }

    companion object {
        private const val PAYLOAD = "payload"
        private const val KEY_OAUTH_ID = "oauthId"
        private const val KEY_OAUTH_TYPE = "oauthType"
    }
}
