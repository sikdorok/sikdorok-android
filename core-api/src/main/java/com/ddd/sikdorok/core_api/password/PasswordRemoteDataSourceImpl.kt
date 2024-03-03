package com.ddd.sikdorok.core_api.password

import com.ddd.sikdorok.core_api.service.UserService
import com.ddd.sikdorok.data.password.data.PasswordRemoteDataSource
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.login.CheckUserRes
import com.ddd.sikdorok.shared.password.Password
import com.ddd.sikdorok.shared.password.Reset
import com.ddd.sikdorok.shared.password.Verify
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

internal class PasswordRemoteDataSourceImpl @Inject constructor(
    private val passwordService: UserService.FindPassword,
    private val verifyService: UserService.VerifyPassword,
    private val resetService: UserService.ResetPassword
) : PasswordRemoteDataSource {

    override suspend fun findPassword(email: String): ApiResult<CheckUserRes> {
        return passwordService.findPassword(Password.Request(email))
    }

    override suspend fun verifyPassword(body: Verify.Request): ApiResult<CheckUserRes> {
        return verifyService.verifyPassword(body)
    }

    override suspend fun resetPassword(body: Reset.Request): ApiResult<CheckUserRes> {
        return resetService.resetPassword(body)
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PasswordModule {
    @Binds
    @Singleton
    abstract fun bindsPasswordRemoteDataSource(
        dataSource: PasswordRemoteDataSourceImpl
    ): PasswordRemoteDataSource
}
