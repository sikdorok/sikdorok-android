package com.ddd.sikdorok.data.password.data

import com.ddd.sikdorok.shared.base.SikdorokResponse

interface PasswordRemoteDataSource {
    suspend fun findPassword(email: String): SikdorokResponse<Boolean>
}
