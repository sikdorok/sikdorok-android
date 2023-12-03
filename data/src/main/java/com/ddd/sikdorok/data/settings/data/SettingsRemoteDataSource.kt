package com.ddd.sikdorok.data.settings.data

import com.ddd.sikdorok.shared.UserDeviceInfo
import com.ddd.sikdorok.shared.base.SikdorokResponse
import okhttp3.ResponseBody

interface SettingsRemoteDataSource {

    suspend fun getUserDeviceInfo(version : String) : SikdorokResponse<UserDeviceInfo>

    suspend fun setUserLogout() : SikdorokResponse<Boolean>

}