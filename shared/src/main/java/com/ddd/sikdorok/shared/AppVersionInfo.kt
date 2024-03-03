package com.ddd.sikdorok.shared

import com.google.gson.annotations.SerializedName

data class AppInfoRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: AppInfo
)

data class AppInfo(
    @SerializedName("appVersion")
    val appVersion: AppVersion
)

data class AppVersion(
    @SerializedName("appInfoAppVersion")
    val appInfoAppVersion: String,
    @SerializedName("major")
    val major: Int,
    @SerializedName("minor")
    val minor: Int,
    @SerializedName("patch")
    val patch: Int,
    @SerializedName("forceUpdateStatus")
    val forceUpdateStatus: Boolean
)
