package com.ddd.sikdorok.domain.repository

import com.ddd.sikdorok.shared.UserDeviceInfo
import com.ddd.sikdorok.shared.base.SikdorokResponse

interface SettingsRepository {

    suspend fun getUserDeviceInfo(version: String) : SikdorokResponse<UserDeviceInfo>
}