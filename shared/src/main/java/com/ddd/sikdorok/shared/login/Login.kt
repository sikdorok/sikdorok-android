package com.ddd.sikdorok.shared.login

import com.google.gson.annotations.SerializedName

@Suppress("SpellCheckingInspection")
sealed class Request {
    data class Kakao(
        @SerializedName("accessToken") val code: String
    ) : Request()

    data class Sikdorok(
        val email: String,
        val password: String
    ) : Request()

    data class RefreshToken(val refreshToken: String) : Request()
}

data class LoginRes(
    val code: Int,
    val message: String,
    val data: Response
)

data class Response(
    val isRegistered: Boolean,
    @SerializedName("usersInfo") val login: Login
) {
    data class Login(
        @SerializedName("usersId") val userId: String,
        val accessToken: String?,
        val refreshToken: String?,
        val lastLoginAt: String,
        val email: String?,
        val nickname: String?,
        val oauthId: String?,
        val oauthType: String?
    )
}

data class RefreshTokenRes(
    val code: Int,
    val message: String,
    val data: String
)
