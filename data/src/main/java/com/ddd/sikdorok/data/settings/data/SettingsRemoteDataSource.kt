package com.ddd.sikdorok.data.settings.data

import com.ddd.sikdorok.shared.UserDeviceInfo
import com.ddd.sikdorok.shared.UserDeviceInfoRes
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.base.SikdorokResponse
import okhttp3.ResponseBody

interface SettingsRemoteDataSource {

    suspend fun getUserDeviceInfo(version : String) : ApiResult<UserDeviceInfoRes>

    suspend fun setUserLogout() : ApiResult<Boolean>

}