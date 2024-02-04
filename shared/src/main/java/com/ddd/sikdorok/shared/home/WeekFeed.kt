package com.ddd.sikdorok.shared.home

import com.ddd.sikdorok.shared.code.Icon

data class WeekFeedRes (
    val code: Int,
    val message: String,
    val data: WeekFeed?
)

data class WeekFeed(
    val date: String,
    val weeklyCovers: List<WeeklyCovers>
)

data class WeeklyCovers(
    val week: Int,
    val weeklyFeeds: List<WeeklyFeeds>
)

data class WeeklyFeeds(
    val time: String,
    val icon: String = Icon.NOTHING.code,
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