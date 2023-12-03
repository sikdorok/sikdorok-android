package com.ddd.sikdorok.navigator

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import com.ddd.sikdorok.modify.ModifyActivity
import com.ddd.sikdorok.navigator.modify.ModifyNavigator
import javax.inject.Inject

internal class ModifyNavigatorImpl @Inject constructor() : ModifyNavigator {

    override fun start(context: Context): Intent {
        return Intent(context, ModifyActivity::class.java)
    }

    override fun start(
        context: Context,
        postId: String?,
        postDate: String?
    ): Intent {
        return Intent(context, ModifyActivity::class.java).apply {
            putExtras(
                bundleOf(
                    POST_ID to postId,
                    POST_DATE to postDate
                )
            )
        }
    }

    companion object {
        private const val POST_ID = "post_id"
        private const val POST_DATE = "post_date"
    }
}
