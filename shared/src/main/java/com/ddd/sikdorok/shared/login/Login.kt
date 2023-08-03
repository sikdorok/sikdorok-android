package com.ddd.sikdorok.shared.login

import com.ddd.sikdorok.shared.base.Response
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
}

data class Response(
    val isRegistered: Boolean,
    @SerializedName("usersInfo") val login: Login
): Response {
    data class Login(
        @SerializedName("usersId") val userId: String,
        val accessToken: String?,
        val lastLoginAt: String,
        val email: String?,
        val nickname: String?
    )
}
