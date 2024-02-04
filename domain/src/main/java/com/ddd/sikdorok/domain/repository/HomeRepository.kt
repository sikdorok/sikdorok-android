package com.ddd.sikdorok.domain.repository

import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.home.HomeFeedRes
import com.ddd.sikdorok.shared.home.ListFeedRes
import com.ddd.sikdorok.shared.home.WeekFeedRes

interface HomeRepository {

    suspend fun getHomeMonthlyFeeds(date: String): ApiResult<WeekFeedRes>

    suspend fun getHomeDailyFeeds(
        page: Int,
        size: Int,
        date: String,
        tag: String? = null
    ): ApiResult<HomeFeedRes>

    suspend fun getHomeListFeeds(
        size: Int,
        date: String,
        cursorDate: String?
    ): ApiResult<ListFeedRes>
}