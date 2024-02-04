package com.ddd.sikdorok.core_api.service

import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.home.HomeFeed
import com.ddd.sikdorok.shared.home.HomeFeedRes
import com.ddd.sikdorok.shared.home.ListFeed
import com.ddd.sikdorok.shared.home.ListFeedRes
import com.ddd.sikdorok.shared.home.WeekFeedRes
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {

    @GET("/home/monthly")
    suspend fun getHomeMonthlyFeeds(
        @Query("date") date: String
    ): ApiResult<WeekFeedRes>

    @GET("/home/list")
    suspend fun getHomeDailyFeeds(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("date") date: String,
        @Query("tag") tag: String?
    ): ApiResult<HomeFeedRes>

    @GET("/home/list-view")
    suspend fun getHomeListFeeds(
        @Query("size") size: Int,
        @Query("date") date: String,
        @Query("cursorDate") cursorDate: String?
    ): ApiResult<ListFeedRes>
}