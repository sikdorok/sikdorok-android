package com.ddd.sikdorok.data.login

import com.ddd.sikdorok.data.SikdorokPreference
import com.ddd.sikdorok.data.login.data.LoginRemoteDataSource
import com.ddd.sikdorok.domain.repository.LoginRepository
import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.key.Keys
import com.ddd.sikdorok.shared.login.Response
import com.ddd.sikdorok.shared.login.TokenType
import com.ddd.sikdorok.shared.sign.SignUp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("SpellCheckingInspection")
internal class LoginRepositoryImpl constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val sikdorokPreference: SikdorokPreference
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

    override fun onPostSaveToken(type: TokenType, token: String): Result<Unit> {
        return runCatching {
            when(type) {
                TokenType.ACCESS_TOKEN -> { sikdorokPreference.savePref(Keys.ACCESS_TOKEN, token) }
                TokenType.REFRESH_TOKEN -> { sikdorokPreference.savePref(Keys.REFRESH_TOKEN, token) }
            }
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
@Suppress("SpellCheckingInspection")
internal object LoginRepositoryModule {
    @Provides
    @Singleton
    fun providesLoginRepository(
        loginRemoteDataSource: LoginRemoteDataSource,
        sikdorokPreference: SikdorokPreference
    ): LoginRepository {
        return LoginRepositoryImpl(loginRemoteDataSource, sikdorokPreference)
    }
}
