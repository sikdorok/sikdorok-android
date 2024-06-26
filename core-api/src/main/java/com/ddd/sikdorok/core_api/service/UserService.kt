package com.ddd.sikdorok.core_api.service

import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.base.BaseResponse
import com.ddd.sikdorok.shared.login.CheckUserRes
import com.ddd.sikdorok.shared.login.LoginRes
import com.ddd.sikdorok.shared.login.RefreshTokenRes
import com.ddd.sikdorok.shared.login.Request
import com.ddd.sikdorok.shared.login.UserProfileReq
import com.ddd.sikdorok.shared.login.UserProfileRes
import com.ddd.sikdorok.shared.password.Password
import com.ddd.sikdorok.shared.password.Reset
import com.ddd.sikdorok.shared.password.Verify
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import com.ddd.sikdorok.shared.sign.SignUp as SikdorokSignUp

interface UserService {
    interface SikdorokLogin {
        @POST("/users/kakao/login")
        suspend fun requestkakaoLogin(@Body body: Request.Kakao): ApiResult<LoginRes>

        @POST("/users/login")
        suspend fun requestLogin(@Body body: Request.Sikdorok): ApiResult<LoginRes>
    }

    interface Token {
        @POST("/users/access-token")
        suspend fun postRefreshToken(@Body body: Request.RefreshToken): RefreshTokenRes
    }

    interface RefreshToken {
        @POST("/users/access-token")
        suspend fun postRefreshToken(@Body body: Request.RefreshToken): RefreshTokenRes
    }

    interface SignUp {
        @POST("/users/register")
        suspend fun requestRegisterUser(@Body body: SikdorokSignUp.Request): ApiResult<LoginRes>
    }

    interface EmailCheck {
        @GET("/users/email-check/{email}")
        suspend fun validateEmail(@Path("email") email: String): ApiResult<CheckUserRes>
    }

    interface FindPassword {
        @POST("/users/password-find")
        suspend fun findPassword(@Body body: Password.Request): ApiResult<CheckUserRes>
    }

    interface VerifyPassword {
        @POST("/users/password-link-alive")
        suspend fun verifyPassword(@Body body: Verify.Request): ApiResult<CheckUserRes>
    }

    interface ResetPassword {
        @PUT("/users/password-reset")
        suspend fun resetPassword(@Body body: Reset.Request): ApiResult<CheckUserRes>
    }

    interface WithDraw {
        @DELETE("/users")
        suspend fun withdrawUser(): ApiResult<BaseResponse>
    }

    interface Settings {
        @GET("/users/profile")
        suspend fun getUserProfile(): ApiResult<UserProfileRes>

        @PUT("/users/profile")
        suspend fun putUserProfile(@Body body: UserProfileReq): ApiResult<BaseResponse>
    }
}