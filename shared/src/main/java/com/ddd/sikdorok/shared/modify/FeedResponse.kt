package com.ddd.sikdorok.shared.modify

import com.ddd.sikdorok.shared.home.HomeDailyFeedPhotoInfo
import com.google.gson.annotations.SerializedName

data class CreateFeedRes(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: String?
)

data class FeedRes(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: FeedResponse
)

data class FeedResponse(
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("feedInfo")
    val feedInfo: FeedInfo
)

data class FeedInfo(
    @SerializedName("feedId")
    val feedId: String,
    @SerializedName("isMine")
    val isMine: Boolean,
    @SerializedName("tag")
    val tag: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("memo")
    val memo: String?,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("isMain")
    val isMain: Boolean,
    @SerializedName("photosInfoList")
    val photosInfoList: List<HomeDailyFeedPhotoInfo>?,
    @SerializedName("photosLimit")
    val photosLimit: Int
)