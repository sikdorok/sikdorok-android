package com.ddd.sikdorok.shared.sign

sealed class SignIn {
    data class Request(
        val nickname: String,
        val email: String,
        val password: String,
        val passwordCheck: String
    ) : SignIn()
}
