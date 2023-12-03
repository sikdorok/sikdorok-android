package com.ddd.sikdorok.navigator

import android.content.Context
import android.content.Intent
import com.ddd.sikdorok.navigator.management.ManagementNavigator
import javax.inject.Inject

internal class ManagementNavigatorImpl @Inject constructor(): ManagementNavigator {
    override fun start(context: Context, payload: ManagementNavigator.Payload): Intent {
        return Intent()
    }

    override fun start(context: Context): Intent {
        return Intent()
    }
}
