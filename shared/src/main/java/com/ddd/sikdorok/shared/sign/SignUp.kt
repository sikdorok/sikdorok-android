package com.ddd.sikdorok.shared.sign

sealed class SignUp {
    data class Request(
        val nickname: String,
        val email: String,
        val password: String,
        val passwordCheck: String
    ) : SignUp()
}
