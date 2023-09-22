package com.ddd.sikdorok.navigator

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import com.ddd.sikdorok.modify.ModifyActivity
import com.ddd.sikdorok.navigator.modify.ModifyNavigator
import com.ddd.sikdorok.signup.SignUpActivity
import javax.inject.Inject

internal class ModifyNavigatorImpl @Inject constructor() : ModifyNavigator {

    override fun start(context: Context): Intent {
        return Intent(context, ModifyActivity::class.java)
    }

    override fun start(context: Context, postId: String?): Intent {
        return Intent(context, ModifyActivity::class.java).apply {
            putExtras(
                bundleOf(
                    POST_ID to postId
                )
            )
        }
    }

    companion object {
        private const val POST_ID = "post_id"
    }
}
