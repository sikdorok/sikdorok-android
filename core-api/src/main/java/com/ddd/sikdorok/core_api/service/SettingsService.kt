package com.ddd.sikdorok.core_api.service

import com.ddd.sikdorok.shared.UserDeviceInfo
import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.home.HomeFeed
import com.ddd.sikdorok.shared.home.WeekFeed
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SettingsService {

    @GET("/users/settings")
    suspend fun getUserDeviceInfo(
        @Query("version") version: String,
        @Query("deviceType") deviceType: String = DEVICE_TYPE,
    ): SikdorokResponse<UserDeviceInfo>

    @POST("/users/logout")
    suspend fun setUserLogout(): SikdorokResponse<Boolean>

    companion object {
        const val DEVICE_TYPE = "C000700001"
    }
}