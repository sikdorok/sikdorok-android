package com.ddd.sikdorok.data.password.data

import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.password.Reset
import com.ddd.sikdorok.shared.password.Verify

interface PasswordRemoteDataSource {
    suspend fun findPassword(email: String): ApiResult<Boolean>

    suspend fun verifyPassword(body: Verify.Request): ApiResult<Boolean>

    suspend fun resetPassword(body: Reset.Request): ApiResult<Boolean>
}
