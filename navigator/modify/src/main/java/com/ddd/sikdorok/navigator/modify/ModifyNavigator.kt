package com.ddd.sikdorok.navigator.modify

import android.content.Context
import android.content.Intent
import com.ddd.sikdorok.navigator.core.Navigator

interface ModifyNavigator : Navigator {
    fun start(
        context: Context,
        postId: String? = null,
        postDate: String? = null
    ): Intent
}