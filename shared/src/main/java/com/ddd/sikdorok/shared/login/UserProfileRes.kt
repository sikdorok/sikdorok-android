package com.ddd.sikdorok.shared.login

import com.google.gson.annotations.SerializedName

data class UserProfileRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: UserProfile
)

data class UserProfile(
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("email")
    val email: String
)

data class UserProfileReq(
    @SerializedName("nickname")
    val nickname: String
)