package com.ddd.sikdorok.domain.repository

import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.home.HomeFeed
import com.ddd.sikdorok.shared.home.ListFeed
import com.ddd.sikdorok.shared.home.WeekFeed

interface HomeRepository {

    suspend fun getHomeMonthlyFeeds(date: String): SikdorokResponse<WeekFeed>

    suspend fun getHomeDailyFeeds(
        page: Int,
        size: Int,
        date: String,
        tag: String
    ): SikdorokResponse<HomeFeed>

    suspend fun getHomeListFeeds(
        size: Int,
        date: String,
        cursorDate: String?
    ): SikdorokResponse<ListFeed>
}