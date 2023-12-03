package com.ddd.sikdorok.navigator

import android.content.Context
import android.content.Intent
import com.ddd.sikdorok.splash.SplashActivity
import com.ddd.sikdorok.splash.SplashNavigator
import javax.inject.Inject

// 추후 로그아웃, 회원탈퇴 이후 스플래시 화면으로 이동을 위함
internal class SplashNavigatorImpl @Inject constructor() : SplashNavigator {

    override fun start(context: Context): Intent {
        return Intent(context, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
}
