package com.ddd.sikdorok.data.login

import com.ddd.sikdorok.data.SikdorokPreference
import com.ddd.sikdorok.data.login.data.LoginRemoteDataSource
import com.ddd.sikdorok.domain.repository.LoginRepository
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.key.Keys
import com.ddd.sikdorok.shared.login.CheckUserRes
import com.ddd.sikdorok.shared.login.LoginRes
import com.ddd.sikdorok.shared.login.RefreshTokenRes
import com.ddd.sikdorok.shared.login.Request
import com.ddd.sikdorok.shared.login.TokenType
import com.ddd.sikdorok.shared.sign.SignUp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("SpellCheckingInspection")
internal class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val sikdorokPreference: SikdorokPreference
) : LoginRepository {

    override suspend fun onCheckSikdorokEmail(email: String): ApiResult<CheckUserRes> {
        return loginRemoteDataSource.onCheckSikdorokEmail(email)
    }

    override suspend fun onCheckSikdorokUser(code: String): ApiResult<LoginRes> {
        return loginRemoteDataSource.onCheckSikdorokUser(code)
    }

    override suspend fun onRequestSikdorokLocalUser(body: Request.Sikdorok): ApiResult<LoginRes> {
        return loginRemoteDataSource.onRequestSikdorokLocalUser(body)
    }

    override suspend fun onSignUpUser(body: SignUp.Request): ApiResult<LoginRes> {
        return loginRemoteDataSource.onSignUpUser(body)
    }

    override fun onPostSaveToken(type: TokenType, token: String) {
        when (type) {
            TokenType.ACCESS_TOKEN -> {
                sikdorokPreference.savePref(Keys.ACCESS_TOKEN, token.orEmpty())
            }
            TokenType.REFRESH_TOKEN -> {
                sikdorokPreference.savePref(Keys.REFRESH_TOKEN, token.orEmpty())
            }
        }
    }

    override fun onGetSavedToken(key: String): String {
        return sikdorokPreference.getString(key)
    }

    override suspend fun postRefreshToken(refreshToken: String): RefreshTokenRes {
        return loginRemoteDataSource.postRefreshToken(refreshToken)
    }
}

@Module
@InstallIn(SingletonComponent::class)
@Suppress("SpellCheckingInspection")
internal abstract class LoginRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository
}
