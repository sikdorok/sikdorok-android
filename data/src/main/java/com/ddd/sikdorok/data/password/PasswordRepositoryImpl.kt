package com.ddd.sikdorok.data.password

import com.ddd.sikdorok.data.password.data.PasswordRemoteDataSource
import com.ddd.sikdorok.domain.repository.PasswordRepository
import com.ddd.sikdorok.shared.base.SikdorokResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

internal class PasswordRepositoryImpl @Inject constructor(
    private val passwordRemoteDataSource: PasswordRemoteDataSource
): PasswordRepository {

    override suspend fun findPassword(email: String): SikdorokResponse<Boolean> {
        return passwordRemoteDataSource.findPassword(email)
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
