package com.ddd.sikdorok.shared.home

import com.ddd.sikdorok.shared.code.Icon
import com.ddd.sikdorok.shared.code.Tag

data class HomeFeedRes(
    val code: Int,
    val message: String,
    val data: HomeFeed?
)

data class HomeFeed(
    val paging: HomeListPaging,
    val dailyFeeds: List<HomeDailyFeed>,
    val initTag : String?,
    val tags : List<String>
)

data class HomeListPaging(
    val number: Int = 0,
    val totalPage: Int = 0,
    val totalElements: Int = 0
)

data class HomeDailyFeed(
    val feedId: String,
    val icon: String,
    val isMain: Boolean,
    val tag: String,
    val time: String,
    val memo: String?,
    val photosInfoList: List<HomeDailyFeedPhotoInfo>?,
) {
    companion object {
        val emptyListItem = listOf(
            HomeDailyFeed(
                feedId = "",
                icon = Icon.NOTHING.code,
                isMain = false,
                tag = Tag.MORNING.code,
                time = "",
                memo = null,
                photosInfoList = null
            )
        )
    }
}

data class HomeDailyFeedPhotoInfo(
    val token: String?,
    val uploadFullPath: String?
)
