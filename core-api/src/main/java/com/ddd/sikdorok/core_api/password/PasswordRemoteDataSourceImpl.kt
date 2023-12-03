package com.ddd.sikdorok.core_api.password

import com.ddd.sikdorok.core_api.service.UserService
import com.ddd.sikdorok.data.password.data.PasswordRemoteDataSource
import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.password.Password
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

internal class PasswordRemoteDataSourceImpl @Inject constructor(
    private val passwordService: UserService.FindPassword
): PasswordRemoteDataSource {

    override suspend fun findPassword(email: String): SikdorokResponse<Boolean> {
        return passwordService.findPassword(Password.Request(email))
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
