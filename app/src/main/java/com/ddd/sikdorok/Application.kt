package com.ddd.sikdorok

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.v2.auth.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.native_key))
        Timber.plant(Timber.DebugTree())

    }
}
