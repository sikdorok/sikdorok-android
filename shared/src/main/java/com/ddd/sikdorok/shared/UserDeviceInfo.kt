package com.ddd.sikdorok.shared

import com.google.gson.annotations.SerializedName

data class UserDeviceInfoRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: UserDeviceInfo
)

data class UserDeviceInfo(
    @SerializedName("oauthType")
    val oauthType: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("email")
    val email: String,
    @SerializedName("isLatest")
    val isLatest: Boolean
)