package com.ddd.sikdorok.shared.base

import com.google.gson.annotations.SerializedName

@Suppress("SpellCheckingInspection")
enum class SikdorokCode {
    @SerializedName("C000100001") SUPER_OWNER,
    @SerializedName("C000100002") OWNER,
    @SerializedName("C000200001") KAKAO_OAUTH
}
