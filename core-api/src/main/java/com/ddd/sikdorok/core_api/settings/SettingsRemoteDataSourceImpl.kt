package com.ddd.sikdorok.core_api.settings

import com.ddd.sikdorok.core_api.service.SettingsService
import com.ddd.sikdorok.data.settings.data.SettingsRemoteDataSource
import com.ddd.sikdorok.shared.UserDeviceInfo
import com.ddd.sikdorok.shared.UserDeviceInfoRes
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.base.SikdorokResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

internal class SettingsRemoteDataSourceImpl @Inject constructor(
    private val settingsService: SettingsService
) : SettingsRemoteDataSource {

    override suspend fun getUserDeviceInfo(version: String): ApiResult<UserDeviceInfoRes> {
        return settingsService.getUserDeviceInfo(version)
    }

    override suspend fun setUserLogout(): ApiResult<Boolean> {
        return settingsService.setUserLogout()
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PasswordModule {
    @Binds
    @Singleton
    abstract fun bindsSettingsRemoteDataSource(
        dataSource: SettingsRemoteDataSourceImpl
    ): SettingsRemoteDataSource
}
