package com.ddd.sikdorok.navigator

import android.content.Context
import android.content.Intent
import com.ddd.sikdorok.navigator.profile.ProfileNavigator
import com.ddd.sikdorok.settings.profile.SettingProfileActivity
import javax.inject.Inject

internal class ProfileNavigatorImpl @Inject constructor() : ProfileNavigator {
    override fun start(context: Context): Intent {
        return Intent(context, SettingProfileActivity::class.java)
    }
}