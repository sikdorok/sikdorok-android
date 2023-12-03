package com.ddd.sikdorok.shared.password

sealed class Password {
    data class Request(val email: String) : Password()
}
