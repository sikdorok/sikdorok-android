package com.ddd.sikdorok.navigator

import android.content.Context
import android.content.Intent
import com.ddd.sikdorok.home.HomeActivity
import com.ddd.sikdorok.home.HomeNavigator
import javax.inject.Inject

internal class HomeNavigatorImpl @Inject constructor() : HomeNavigator {

    override fun start(context: Context): Intent {
        return Intent(context, HomeActivity::class.java)
    }

}
