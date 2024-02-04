package com.ddd.sikdorok.data.withdraw.data

import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.base.SikdorokResponse

interface UserWithdrawRemoteDataSource {
    suspend fun withdrawUser(): ApiResult<Unit>

    fun refreshUserSetting(): Unit
}
