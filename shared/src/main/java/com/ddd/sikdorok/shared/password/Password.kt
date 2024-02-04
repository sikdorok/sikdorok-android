package com.ddd.sikdorok.shared.password

import com.google.gson.annotations.SerializedName

sealed class Password {
    data class Request(val email: String) : Password()
}

sealed interface Verify {
    data class Request(
        @SerializedName("userId") val userId: String,
        @SerializedName("code") val code: String
    ) : Verify
}

sealed interface Reset {
    data class Request(
        @SerializedName("userId") val userId: String,
        @SerializedName("password") val password: String,
        @SerializedName("passwordCheck") val passwordCheck: String
    ) : Verify
}