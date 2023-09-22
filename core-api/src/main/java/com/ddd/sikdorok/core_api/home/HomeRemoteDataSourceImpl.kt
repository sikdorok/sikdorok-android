package com.ddd.sikdorok.core_api.home

import com.ddd.sikdorok.core_api.service.HomeService
import com.ddd.sikdorok.data.home.data.HomeRemoteDataSource
import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.home.HomeFeed
import com.ddd.sikdorok.shared.home.ListFeed
import com.ddd.sikdorok.shared.home.WeekFeed
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

internal class HomeRemoteDataSourceImpl @Inject constructor(
    private val sikdorokHomeService: HomeService,
) : HomeRemoteDataSource {

    override suspend fun getHomeMonthlyFeeds(date: String): SikdorokResponse<WeekFeed> {
        return sikdorokHomeService.getHomeMonthlyFeeds(date)
    }

    override suspend fun getHomeDailyFeeds(
        page: Int,
        size: Int,
        date: String,
        tag: String
    ): SikdorokResponse<HomeFeed> {
        return sikdorokHomeService.getHomeDailyFeeds(page, size, date, tag)
    }

    override suspend fun getHomeListFeeds(
        size: Int,
        date: String,
        cursorDate: String?
    ): SikdorokResponse<ListFeed> {
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
