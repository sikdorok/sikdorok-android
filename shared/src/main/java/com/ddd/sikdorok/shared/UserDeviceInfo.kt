package com.ddd.sikdorok.shared

data class UserDeviceInfo(
    val oauthType: String?,
    val nickname: String?,
    val email: String,
    val isLatest: Boolean
)