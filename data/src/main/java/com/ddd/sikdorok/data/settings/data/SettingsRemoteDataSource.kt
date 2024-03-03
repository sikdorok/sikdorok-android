package com.ddd.sikdorok.data.settings.data

import com.ddd.sikdorok.shared.AppInfoRes
import com.ddd.sikdorok.shared.UserDeviceInfoRes
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.base.BaseResponse
import com.ddd.sikdorok.shared.login.UserProfileRes

interface SettingsRemoteDataSource {

    suspend fun getUserDeviceInfo(version: String): ApiResult<UserDeviceInfoRes>

    suspend fun setUserLogout(): ApiResult<BaseResponse>

    suspend fun getUserprofile(): ApiResult<UserProfileRes>

    suspend fun putUserProfile(nickName: String): ApiResult<BaseResponse>

    suspend fun getAppInfo(): ApiResult<AppInfoRes>
}