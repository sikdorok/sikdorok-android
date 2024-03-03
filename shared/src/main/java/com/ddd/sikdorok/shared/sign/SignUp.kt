package com.ddd.sikdorok.shared.sign

import com.google.gson.annotations.SerializedName

sealed class SignUp {
    data class Request(
        @SerializedName("oauthType")
        val oauthType: String?,
        @SerializedName("oauthId")
        val oauthId: Long?,
        @SerializedName("nickname")
        val nickname: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("passwordCheck")
        val passwordCheck: String
    ) : SignUp()
}
