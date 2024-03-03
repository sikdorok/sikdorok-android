package com.ddd.sikdorok.shared.home

import com.ddd.sikdorok.shared.code.Icon
import com.ddd.sikdorok.shared.code.Tag
import com.google.gson.annotations.SerializedName

data class HomeFeedRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: HomeFeed?
)

data class HomeFeed(
    @SerializedName("paging")
    val paging: HomeListPaging,
    @SerializedName("dailyFeeds")
    val dailyFeeds: List<HomeDailyFeed>,
    @SerializedName("initTag")
    val initTag : String?,
    @SerializedName("tags")
    val tags : List<String>
)

data class HomeListPaging(
    @SerializedName("number")
    val number: Int = 0,
    @SerializedName("totalPage")
    val totalPage: Int = 0,
    @SerializedName("totalElements")
    val totalElements: Int = 0
)

data class HomeDailyFeed(
    @SerializedName("feedId")
    val feedId: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("isMain")
    val isMain: Boolean,
    @SerializedName("tag")
    val tag: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("memo")
    val memo: String?,
    @SerializedName("photosInfoList")
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
    @SerializedName("token")
    val token: String?,
    @SerializedName("uploadFullPath")
    val uploadFullPath: String?
)
