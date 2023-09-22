package com.ddd.sikdorok.data.settings.data

import com.ddd.sikdorok.shared.UserDeviceInfo
import com.ddd.sikdorok.shared.base.SikdorokResponse

interface SettingsRemoteDataSource {

    suspend fun getUserDeviceInfo(version : String) : SikdorokResponse<UserDeviceInfo>

}