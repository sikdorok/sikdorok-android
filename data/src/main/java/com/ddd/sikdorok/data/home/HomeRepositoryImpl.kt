package com.ddd.sikdorok.data.home

import com.ddd.sikdorok.data.home.data.HomeRemoteDataSource
import com.ddd.sikdorok.domain.repository.HomeRepository
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.home.HomeFeedRes
import com.ddd.sikdorok.shared.home.ListFeedRes
import com.ddd.sikdorok.shared.home.WeekFeedRes
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

class HomeRepositoryImpl @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource
) : HomeRepository {

    override suspend fun getHomeMonthlyFeeds(date: String): ApiResult<WeekFeedRes> {
        return homeRemoteDataSource.getHomeMonthlyFeeds(date)
    }

    override suspend fun getHomeDailyFeeds(
        page: Int,
        size: Int,
        date: String,
        tag: String?
    ): ApiResult<HomeFeedRes> {
        return homeRemoteDataSource.getHomeDailyFeeds(page, size, date, tag)
    }

    override suspend fun getHomeListFeeds(
        size: Int,
        date: String,
        cursorDate: String?
    ): ApiResult<ListFeedRes> {
        return homeRemoteDataSource.getHomeListFeeds(size, date, cursorDate)
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HomeRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository
}
