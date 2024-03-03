package com.ddd.sikdorok.shared.home

import com.ddd.sikdorok.shared.code.Icon
import com.google.gson.annotations.SerializedName

data class WeekFeedRes (
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: WeekFeed?
)

data class WeekFeed(
    @SerializedName("date")
    val date: String,
    @SerializedName("weeklyCovers")
    val weeklyCovers: List<WeeklyCovers>
)

data class WeeklyCovers(
    @SerializedName("week")
    val week: Int,
    @SerializedName("weeklyFeeds")
    val weeklyFeeds: List<WeeklyFeeds>
)

data class WeeklyFeeds(
    @SerializedName("time")
    val time: String,
    @SerializedName("icon")
    val icon: String = Icon.NOTHING.code,
    @SerializedName("isSelected")
    var isSelected: Boolean
) {
    companion object {
        val emptyWeeklyList = listOf(
            WeeklyFeeds("2023-08-27", Icon.NOTHING.code, false),
            WeeklyFeeds("2023-08-28", Icon.NOTHING.code, false),
            WeeklyFeeds("2023-08-29", Icon.NOTHING.code, false),
            WeeklyFeeds("2023-08-30", Icon.NOTHING.code, false),
            WeeklyFeeds("2023-08-31", Icon.NOTHING.code, false),
            WeeklyFeeds("2023-09-01", Icon.NOTHING.code, false),
            WeeklyFeeds("2023-09-02", Icon.NOTHING.code, false),
        )

        val emptyList =
            emptyWeeklyList + emptyWeeklyList + emptyWeeklyList + emptyWeeklyList + emptyWeeklyList
    }
}