package com.ddd.sikdorok.navigator

import android.content.Context
import android.content.Intent
import com.ddd.sikdorok.navigator.settings.SettingsNavigator
import com.ddd.sikdorok.settings.SettingActivity
import javax.inject.Inject

internal class SettingsNavigatorImpl @Inject constructor() : SettingsNavigator {
    override fun start(context: Context): Intent {
        return Intent(context, SettingActivity::class.java)
    }
}
