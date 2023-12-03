package com.ddd.sikdorok.data.settings

import com.ddd.sikdorok.data.settings.data.SettingsRemoteDataSource
import com.ddd.sikdorok.domain.repository.SettingsRepository
import com.ddd.sikdorok.shared.UserDeviceInfo
import com.ddd.sikdorok.shared.base.SikdorokResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

class SettingsRepositoryImpl @Inject constructor(
    private val settingsRemoteDataSource: SettingsRemoteDataSource
) : SettingsRepository {

    override suspend fun getUserDeviceInfo(version: String): SikdorokResponse<UserDeviceInfo> {
        return settingsRemoteDataSource.getUserDeviceInfo(version)
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
