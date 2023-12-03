package com.ddd.sikdorok.navigator

import android.content.Context
import android.content.Intent
import com.ddd.sikdorok.find_password.FindPasswordActivity
import com.ddd.sikdorok.find_password.FindPasswordNavigator
import javax.inject.Inject

internal class FindPasswordNavigatorImpl @Inject constructor(): FindPasswordNavigator {

    override fun start(context: Context): Intent {
        return Intent(context, FindPasswordActivity::class.java)
    }

}
