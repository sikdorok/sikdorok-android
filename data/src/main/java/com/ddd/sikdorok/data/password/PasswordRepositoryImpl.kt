package com.ddd.sikdorok.data.password

import com.ddd.sikdorok.data.password.data.PasswordRemoteDataSource
import com.ddd.sikdorok.domain.repository.PasswordRepository
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.login.CheckUserRes
import com.ddd.sikdorok.shared.password.Reset
import com.ddd.sikdorok.shared.password.Verify
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

internal class PasswordRepositoryImpl @Inject constructor(
    private val passwordRemoteDataSource: PasswordRemoteDataSource
) : PasswordRepository {

    override suspend fun findPassword(email: String): ApiResult<CheckUserRes> {
        return passwordRemoteDataSource.findPassword(email)
    }

    override suspend fun verifyPassword(body: Verify.Request): ApiResult<CheckUserRes> {
        return passwordRemoteDataSource.verifyPassword(body)
    }

    override suspend fun resetPassword(body: Reset.Request): ApiResult<CheckUserRes> {
        return passwordRemoteDataSource.resetPassword(body)
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PasswordRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsPasswordRepository(
        repository: PasswordRepositoryImpl
    ): PasswordRepository
}
