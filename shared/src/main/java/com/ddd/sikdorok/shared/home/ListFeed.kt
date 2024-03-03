package com.ddd.sikdorok.shared.home

import com.google.gson.annotations.SerializedName

data class ListFeedRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: ListFeed?
)

data class ListFeed(
    @SerializedName("hasNext")
    val hasNext: Boolean,
    @SerializedName("cursorDate")
    val cursorDate: String,
    @SerializedName("dailyFeeds")
    val dailyFeeds: List<DailyFeed>
)

data class DailyFeed(
    @SerializedName("date")
    val date: String,
    @SerializedName("feeds")
    val feeds: Feed
)

data class Feed(
    @SerializedName("morning")
    val morning: List<FeedItem>,
    @SerializedName("afternoon")
    val afternoon: List<FeedItem>,
    @SerializedName("evening")
    val evening: List<FeedItem>,
    @SerializedName("snack")
    val snack: List<FeedItem>,
)

data class FeedItem(
    @SerializedName("feedId")
    val feedId: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("isMain")
    val isMain: Boolean,
    @SerializedName("time")
    val time: String,
    @SerializedName("memo")
    val memo: String?,
    @SerializedName("photosInfoList")
    val photosInfoList: List<HomeDailyFeedPhotoInfo>?
)