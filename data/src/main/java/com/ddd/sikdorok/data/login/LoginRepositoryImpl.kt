package com.ddd.sikdorok.data.login

import com.ddd.sikdorok.data.login.data.LoginRemoteDataSource
import com.ddd.sikdorok.domain.repository.LoginRepository
import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.login.Response
import com.ddd.sikdorok.shared.sign.SignUp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

internal class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource
): LoginRepository {

    override suspend fun onCheckSikdorokEmail(email: String): SikdorokResponse<Boolean> {
        return loginRemoteDataSource.onCheckSikdorokEmail(email)
    }

    override suspend fun onCheckSikdorokUser(code: String): SikdorokResponse<Response> {
        return loginRemoteDataSource.onCheckSikdorokUser(code)
    }

    override suspend fun onSignUpUser(body: SignUp.Request): SikdorokResponse<Response> {
        return loginRemoteDataSource.onSignUpUser(body)
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LoginRepositoryModule {
    @Binds
    abstract fun bindsLoginRepository(repository: LoginRepositoryImpl): LoginRepository
}
