package com.ddd.sikdorok.shared.password

import com.google.gson.annotations.SerializedName

sealed class Password {
    data class Request(
        @SerializedName("email")
        val email: String
    ) : Password()
}

sealed interface Verify {
    data class Request(
        @SerializedName("usersId")
        val userId: String,
        @SerializedName("code")
        val code: String
    ) : Verify
}

sealed interface Reset {
    data class Request(
        @SerializedName("usersId")
        val userId: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("passwordCheck")
        val passwordCheck: String
    ) : Verify
}