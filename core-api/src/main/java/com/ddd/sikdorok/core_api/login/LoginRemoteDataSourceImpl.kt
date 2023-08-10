package com.ddd.sikdorok.core_api.login

import com.ddd.sikdorok.core_api.service.UserService
import com.ddd.sikdorok.data.login.data.LoginRemoteDataSource
import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.login.Request
import com.ddd.sikdorok.shared.login.Response
import com.ddd.sikdorok.shared.sign.SignUp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

internal class LoginRemoteDataSourceImpl @Inject constructor(
    private val sikdorokLoginService: UserService.SikdorokLogin,
    private val sikdorokSignUpService : UserService.SignUp
): LoginRemoteDataSource {
    override suspend fun onCheckSikdorokUser(code: String): SikdorokResponse<Response> {
        return sikdorokLoginService.requestkakaoLogin(Request.Kakao(code))
    }

    override suspend fun onSignUpUser(body: SignUp.Request): SikdorokResponse<Response> {
        return sikdorokSignUpService.requestRegisterUser(body)
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal object LoginModule {
    @Provides
    fun providesLoginRemoteData(
        loginService: UserService.SikdorokLogin,
        signUpService: UserService.SignUp
    ): LoginRemoteDataSource {
        return LoginRemoteDataSourceImpl(loginService, signUpService)
    }
}