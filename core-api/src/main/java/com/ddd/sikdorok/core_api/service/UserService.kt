package com.ddd.sikdorok.core_api.service

import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.login.Request
import com.ddd.sikdorok.shared.login.Response
import com.ddd.sikdorok.shared.password.Password
import com.ddd.sikdorok.shared.sign.SignUp
import com.ddd.sikdorok.shared.sign.SignUp as SikdorokSignUp
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    interface SikdorokLogin {
        @POST("/users/kakao/login")
        suspend fun requestkakaoLogin(@Body body: Request.Kakao): SikdorokResponse<Response>

        @POST("/users/login")
        suspend fun requestLogin(@Body body: Request.Sikdorok): SikdorokResponse<Response>
    }

    interface Token {
        @POST("/users/access-token")
        suspend fun postRefreshToken(@Body body: Request.RefreshToken): SikdorokResponse<String>
    }

    interface SignUp {
        @POST("/users/register")
        suspend fun requestRegisterUser(@Body body: SikdorokSignUp.Request): SikdorokResponse<Response>
    }

    interface EmailCheck {
        @GET("/users/email-check/{email}")
        suspend fun validateEmail(@Path("email") email: String): SikdorokResponse<Boolean>
    }

    interface FindPassword {
        @POST("/users/password-find")
        suspend fun findPassword(@Body body: Password.Request): SikdorokResponse<Boolean>
    }

    interface WithDraw {
        @DELETE("/users")
        suspend fun withdrawUser(): SikdorokResponse<Unit>
    }
}
