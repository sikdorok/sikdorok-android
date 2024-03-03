package com.ddd.sikdorok.core_api.login

import com.ddd.sikdorok.core_api.service.UserService
import com.ddd.sikdorok.data.login.data.LoginRemoteDataSource
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.login.CheckUserRes
import com.ddd.sikdorok.shared.login.LoginRes
import com.ddd.sikdorok.shared.login.RefreshTokenRes
import com.ddd.sikdorok.shared.login.Request
import com.ddd.sikdorok.shared.sign.SignUp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("SpellCheckingInspection")
internal class LoginRemoteDataSourceImpl @Inject constructor(
    private val sikdorokLoginService: UserService.SikdorokLogin,
    private val sikdorokSignUpService: UserService.SignUp,
    private val sikdorokEmailService: UserService.EmailCheck,
    private val sikdorokRefreshTokenService: UserService.RefreshToken
) : LoginRemoteDataSource {

    override suspend fun onCheckSikdorokEmail(email: String): ApiResult<CheckUserRes> {
        return sikdorokEmailService.validateEmail(email)
    }

    override suspend fun onCheckSikdorokUser(code: String): ApiResult<LoginRes> {
        return sikdorokLoginService.requestkakaoLogin(Request.Kakao(code))
    }

    override suspend fun onRequestSikdorokLocalUser(body: Request.Sikdorok): ApiResult<LoginRes> {
        return sikdorokLoginService.requestLogin(body)
    }

    override suspend fun onSignUpUser(body: SignUp.Request): ApiResult<LoginRes> {
        return sikdorokSignUpService.requestRegisterUser(body)
    }

    override suspend fun postRefreshToken(refreshToken: String): RefreshTokenRes {
        return sikdorokRefreshTokenService.postRefreshToken(Request.RefreshToken(refreshToken))
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal object LoginModule {
    @Provides
    @Singleton
    fun providesLoginRemoteData(
        loginService: UserService.SikdorokLogin,
        signUpService: UserService.SignUp,
        emailService: UserService.EmailCheck,
        refreshTokenService: UserService.RefreshToken
    ): LoginRemoteDataSource {
        return LoginRemoteDataSourceImpl(
            loginService,
            signUpService,
            emailService,
            refreshTokenService
        )
    }
}
