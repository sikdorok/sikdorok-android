package com.ddd.sikdorok.shared

data class UserDeviceInfoRes(
    val code: Int,
    val message: String,
    val data: UserDeviceInfo
)

data class UserDeviceInfo(
    val oauthType: String?,
    val nickname: String?,
    val email: String,
    val isLatest: Boolean
)