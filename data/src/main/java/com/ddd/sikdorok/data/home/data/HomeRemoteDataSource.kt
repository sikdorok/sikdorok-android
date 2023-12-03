package com.ddd.sikdorok.data.home.data

import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.home.HomeFeed
import com.ddd.sikdorok.shared.home.ListFeed
import com.ddd.sikdorok.shared.home.WeekFeed

interface HomeRemoteDataSource {

    suspend fun getHomeMonthlyFeeds(date: String): SikdorokResponse<WeekFeed>

    suspend fun getHomeDailyFeeds(
        page: Int,
        size: Int,
        date: String,
        tag: String? = null
    ): SikdorokResponse<HomeFeed>

    suspend fun getHomeListFeeds(
        size: Int,
        date: String,
        cursorDate: String?
    ): SikdorokResponse<ListFeed>

}