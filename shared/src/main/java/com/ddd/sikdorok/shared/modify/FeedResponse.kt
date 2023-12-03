package com.ddd.sikdorok.shared.modify

import com.ddd.sikdorok.shared.home.HomeDailyFeedPhotoInfo

data class FeedResponse(
    val nickname: String?,
    val feedInfo: FeedInfo
)

data class FeedInfo(
    val feedId: String,
    val isMine: Boolean,
    val tag: String,
    val time: String,
    val memo: String?,
    val icon: String,
    val isMain: Boolean,
    val photosInfoList: List<HomeDailyFeedPhotoInfo>?,
    val photosLimit: Int
)