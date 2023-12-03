package com.ddd.sikdorok.core_api.settings

import com.ddd.sikdorok.core_api.service.SettingsService
import com.ddd.sikdorok.data.settings.data.SettingsRemoteDataSource
import com.ddd.sikdorok.shared.UserDeviceInfo
import com.ddd.sikdorok.shared.base.SikdorokResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

internal class SettingsRemoteDataSourceImpl @Inject constructor(
    private val settingsService: SettingsService
) : SettingsRemoteDataSource {

    override suspend fun getUserDeviceInfo(version: String): SikdorokResponse<UserDeviceInfo> {
        return settingsService.getUserDeviceInfo(version)
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
