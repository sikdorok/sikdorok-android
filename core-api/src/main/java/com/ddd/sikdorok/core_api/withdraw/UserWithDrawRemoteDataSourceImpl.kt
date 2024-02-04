package com.ddd.sikdorok.core_api.withdraw

import com.ddd.sikdorok.core_api.service.UserService
import com.ddd.sikdorok.data.SikdorokPreference
import com.ddd.sikdorok.data.withdraw.data.UserWithdrawRemoteDataSource
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.base.SikdorokResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

internal class UserWithDrawRemoteDataSourceImpl @Inject constructor(
    private val withDrawService: UserService.WithDraw,
    private val sikdorokPreference: SikdorokPreference
): UserWithdrawRemoteDataSource {
    override suspend fun withdrawUser(): ApiResult<Unit> {
        return withDrawService.withdrawUser()
    }

    override fun refreshUserSetting() {
        sikdorokPreference
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UserWithDrawRemoteDataModule {
    @Binds
    @Singleton
    abstract fun bindsUserWithDrawRemoteDataSource(
        dataSource: UserWithDrawRemoteDataSourceImpl
    ): UserWithdrawRemoteDataSource
}


