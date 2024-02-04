package com.ddd.sikdorok.domain.repository

import com.ddd.sikdorok.shared.UserDeviceInfo
import com.ddd.sikdorok.shared.UserDeviceInfoRes
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.base.SikdorokResponse

interface SettingsRepository {
    suspend fun getUserDeviceInfo(version: String) : ApiResult<UserDeviceInfoRes>

    suspend fun setUserLogout() : ApiResult<Boolean>
}