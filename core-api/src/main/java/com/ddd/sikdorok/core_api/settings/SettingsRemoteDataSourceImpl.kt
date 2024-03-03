package com.ddd.sikdorok.core_api.settings

import com.ddd.sikdorok.core_api.service.AppInfoService
import com.ddd.sikdorok.core_api.service.SettingsService
import com.ddd.sikdorok.core_api.service.UserService
import com.ddd.sikdorok.data.settings.data.SettingsRemoteDataSource
import com.ddd.sikdorok.shared.AppInfoRes
import com.ddd.sikdorok.shared.UserDeviceInfoRes
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.base.BaseResponse
import com.ddd.sikdorok.shared.login.UserProfileReq
import com.ddd.sikdorok.shared.login.UserProfileRes
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

internal class SettingsRemoteDataSourceImpl @Inject constructor(
    private val settingsService: SettingsService,
    private val appInfoService : AppInfoService,
    private val userProfileService: UserService.Settings
) : SettingsRemoteDataSource {

    override suspend fun getUserDeviceInfo(version: String): ApiResult<UserDeviceInfoRes> {
        return settingsService.getUserDeviceInfo(version)
    }

    override suspend fun setUserLogout(): ApiResult<BaseResponse> {
        return settingsService.setUserLogout()
    }

    override suspend fun getUserprofile(): ApiResult<UserProfileRes> {
        return userProfileService.getUserProfile()
    }

    override suspend fun putUserProfile(nickName: String): ApiResult<BaseResponse> {
        return userProfileService.putUserProfile(UserProfileReq(nickName))
    }

    override suspend fun getAppInfo(): ApiResult<AppInfoRes> {
        return appInfoService.getAppVersionInfo()
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
