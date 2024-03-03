package com.ddd.sikdorok.shared.modify

import com.ddd.sikdorok.shared.code.Icon
import com.ddd.sikdorok.shared.code.Tag
import com.google.gson.annotations.SerializedName

data class FeedRequest(
    @SerializedName("feedId")
    val feedId: String? = null,
    @SerializedName("tag")
    val tag: Tag,
    @SerializedName("time")
    val time: String,
    @SerializedName("memo")
    val memo: String = "",
    @SerializedName("icon")
    val icon: Icon,
    @SerializedName("isMain")
    val isMain: Boolean,
    @SerializedName("deletePhotoTokens")
    val deletePhotoTokens: List<String> = emptyList()
)
