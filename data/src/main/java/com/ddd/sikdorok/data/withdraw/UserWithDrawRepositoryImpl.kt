package com.ddd.sikdorok.data.withdraw

import com.ddd.sikdorok.data.withdraw.data.UserWithdrawRemoteDataSource
import com.ddd.sikdorok.domain.repository.UserWithDrawRepository
import com.ddd.sikdorok.shared.base.ApiResult
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

internal class UserWithDrawRepositoryImpl @Inject constructor(
    private val userWithdrawRemoteDataSource: UserWithdrawRemoteDataSource
) : UserWithDrawRepository {

    override suspend fun withdrawUser(): ApiResult<Unit> {
        return userWithdrawRemoteDataSource.withdrawUser()
    }

    override fun deleteAllData(): Boolean {
        userWithdrawRemoteDataSource.refreshUserSetting()
        return true
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UserWithDrawModule {
    @Binds
    @Singleton
    abstract fun bindsUserWithDrawRepository(
        repository: UserWithDrawRepositoryImpl
    ): UserWithDrawRepository
}
