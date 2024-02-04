package com.ddd.sikdorok.core_api.home

import com.ddd.sikdorok.core_api.service.HomeService
import com.ddd.sikdorok.data.home.data.HomeRemoteDataSource
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.home.HomeFeed
import com.ddd.sikdorok.shared.home.HomeFeedRes
import com.ddd.sikdorok.shared.home.ListFeed
import com.ddd.sikdorok.shared.home.ListFeedRes
import com.ddd.sikdorok.shared.home.WeekFeedRes
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

internal class HomeRemoteDataSourceImpl @Inject constructor(
    private val sikdorokHomeService: HomeService,
) : HomeRemoteDataSource {

    override suspend fun getHomeMonthlyFeeds(date: String): ApiResult<WeekFeedRes> {
        return sikdorokHomeService.getHomeMonthlyFeeds(date)
    }

    override suspend fun getHomeDailyFeeds(
        page: Int,
        size: Int,
        date: String,
        tag: String?
    ): ApiResult<HomeFeedRes> {
        return sikdorokHomeService.getHomeDailyFeeds(page, size, date, tag)
    }

    override suspend fun getHomeListFeeds(
        size: Int,
        date: String,
        cursorDate: String?
    ): ApiResult<ListFeedRes> {
        return sikdorokHomeService.getHomeListFeeds(size, date, cursorDate)
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HomeRemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindsHomeDataSource(homeDataSourceImpl: HomeRemoteDataSourceImpl): HomeRemoteDataSource
}
