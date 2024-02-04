package com.ddd.sikdorok.shared.home

data class ListFeedRes(
    val code: Int,
    val message: String,
    val data: ListFeed?
)

data class ListFeed(
    val hasNext: Boolean,
    val cursorDate: String,
    val dailyFeeds: List<DailyFeed>
)

data class DailyFeed(
    val date: String,
    val feeds: Feed
)

data class Feed(
    val morning: List<FeedItem>,
    val afternoon: List<FeedItem>,
    val evening: List<FeedItem>,
    val snack: List<FeedItem>,
)

data class FeedItem(
    val feedId: String,
    val icon: String,
    val isMain: Boolean,
    val time: String,
    val memo: String?,
    val photosInfoList: List<HomeDailyFeedPhotoInfo>?
)