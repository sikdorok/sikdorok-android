package com.ddd.sikdorok.core_api.service

import com.ddd.sikdorok.shared.AppInfoRes
import com.ddd.sikdorok.shared.UserDeviceInfoRes
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SettingsService {
    @GET("/users/settings")
    suspend fun getUserDeviceInfo(
        @Query("version") version: String,
        @Query("deviceType") deviceType: String = DEVICE_TYPE,
    ): ApiResult<UserDeviceInfoRes>

    @POST("/users/logout")
    suspend fun setUserLogout(): ApiResult<BaseResponse>

    companion object {
        const val DEVICE_TYPE = "C000700001"
    }
}

interface AppInfoService {
    @GET("/app-version/{type}")
    suspend fun getAppVersionInfo(
        @Path("type") deviceType: String = DEVICE_TYPE
    ): ApiResult<AppInfoRes>

    companion object {
        const val DEVICE_TYPE = "C000700001"
    }
}