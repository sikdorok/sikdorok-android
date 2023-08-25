package com.ddd.sikdorok.domain.repository

import com.ddd.sikdorok.shared.base.SikdorokResponse

interface UserWithDrawRepository {
    suspend fun withdrawUser(): SikdorokResponse<Unit>
}
