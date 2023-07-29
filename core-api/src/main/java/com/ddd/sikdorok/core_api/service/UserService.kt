package com.ddd.sikdorok.core_api.service

import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.login.Request
import com.ddd.sikdorok.shared.login.Response
import com.ddd.sikdorok.shared.sign.SignIn
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    interface KakaoLogin {
        @POST("/users/kakao/login")
        suspend fun requestLogin(body: Request.Kakao): SikdorokResponse<Response>
    }

    interface SDRLogin {
        @POST("/users/login")
        suspend fun requestLogin(body: Request.Sikdorok): SikdorokResponse<Response>
    }

    interface SignUp {
        @POST("/users/register")
        suspend fun requestRegisterUser(body: SignIn.Request): SikdorokResponse<Response>
    }

    interface EmailCheck {
        @GET("GET /users/email-check/")
        suspend fun validateEmail(@Query("email") email: String): SikdorokResponse<Boolean>
    }
}
