package com.ddd.sikdorok.data.settings

import com.ddd.sikdorok.data.settings.data.SettingsRemoteDataSource
import com.ddd.sikdorok.domain.repository.SettingsRepository
import com.ddd.sikdorok.shared.AppInfoRes
import com.ddd.sikdorok.shared.UserDeviceInfo
import com.ddd.sikdorok.shared.UserDeviceInfoRes
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.base.BaseResponse
import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.login.UserProfileRes
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

class SettingsRepositoryImpl @Inject constructor(
    private val settingsRemoteDataSource: SettingsRemoteDataSource
) : SettingsRepository {

    override suspend fun getUserDeviceInfo(version: String): ApiResult<UserDeviceInfoRes> {
        return settingsRemoteDataSource.getUserDeviceInfo(version)
    }

    override suspend fun setUserLogout(): ApiResult<BaseResponse> {
        return settingsRemoteDataSource.setUserLogout()
    }

    override suspend fun getUserprofile(): ApiResult<UserProfileRes> {
        return settingsRemoteDataSource.getUserprofile()
    }

    override suspend fun putUserProfile(nickName: String): ApiResult<BaseResponse> {
        return settingsRemoteDataSource.putUserProfile(nickName)
    }

    override suspend fun getAppInfo(): ApiResult<AppInfoRes> {
        return settingsRemoteDataSource.getAppInfo()
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SettingsRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsSettingsRepository(
        repository: SettingsRepositoryImpl
    ): SettingsRepository
}
