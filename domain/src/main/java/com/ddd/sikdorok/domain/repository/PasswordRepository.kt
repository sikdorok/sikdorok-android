package com.ddd.sikdorok.domain.repository

import com.ddd.sikdorok.shared.base.SikdorokResponse

interface PasswordRepository {
    suspend fun findPassword(email: String): SikdorokResponse<Boolean>
}
