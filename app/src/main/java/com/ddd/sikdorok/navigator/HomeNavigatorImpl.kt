package com.ddd.sikdorok.navigator

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import com.ddd.sikdorok.home.HomeActivity
import com.ddd.sikdorok.home.HomeListNavigator
import com.ddd.sikdorok.home.HomeNavigator
import com.ddd.sikdorok.home.list.HomeListActivity
import javax.inject.Inject

internal class HomeNavigatorImpl @Inject constructor() : HomeNavigator {

    override fun start(context: Context): Intent {
        return Intent(context, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }

    override fun start(context: Context, deeplink: String?): Intent {
        return Intent(context, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            putExtras(bundleOf(KEY_DEEPLINK to deeplink))
        }
    }

    companion object {
        const val KEY_DEEPLINK = "deeplink"
    }
}

internal class HomeListNavigatorImpl @Inject constructor() : HomeListNavigator {

    override fun start(context: Context): Intent {
        return Intent(context, HomeListActivity::class.java)
    }

    override fun start(context: Context, selectedDate: String): Intent {
        return Intent(context, HomeListActivity::class.java).apply {
            putExtras(bundleOf(KEY_SELECTED_DATE to selectedDate))
        }
    }

    companion object {
        const val KEY_SELECTED_DATE = "selected_date"
    }
}
