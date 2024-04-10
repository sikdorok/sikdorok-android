package com.ddd.sikdorok.domain.repository

import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.base.BaseResponse

interface UserWithDrawRepository {
    suspend fun withdrawUser(): ApiResult<BaseResponse>

    fun deleteAllData(): Boolean
}
