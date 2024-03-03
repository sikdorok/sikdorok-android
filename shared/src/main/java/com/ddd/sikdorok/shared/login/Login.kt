package com.ddd.sikdorok.shared.login

import com.google.gson.annotations.SerializedName

@Suppress("SpellCheckingInspection")
sealed class Request {
    data class Kakao(
        @SerializedName("accessToken")
        val code: String
    ) : Request()

    data class Sikdorok(
        @SerializedName("email")
        val email: String,
        @SerializedName("password")
        val password: String
    ) : Request()

    data class RefreshToken(
        @SerializedName("refreshToken")
        val refreshToken: String
    ) : Request()
}

data class LoginRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Response
)

data class Response(
    @SerializedName("isRegistered")
    val isRegistered: Boolean,
    @SerializedName("usersInfo")
    val login: Login
) {
    data class Login(
        @SerializedName("usersId")
        val userId: String,
        @SerializedName("accessToken")
        val accessToken: String?,
        @SerializedName("refreshToken")
        val refreshToken: String?,
        @SerializedName("lastLoginAt")
        val lastLoginAt: String,
        @SerializedName("email")
        val email: String?,
        @SerializedName("nickname")
        val nickname: String?,
        @SerializedName("oauthId")
        val oauthId: String?,
        @SerializedName("oauthType")
        val oauthType: String?
    )
}

data class RefreshTokenRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: String
)

data class CheckUserRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Boolean?
)
