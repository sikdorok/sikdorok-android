package com.ddd.sikdorok.data.home

import com.ddd.sikdorok.data.home.data.HomeRemoteDataSource
import com.ddd.sikdorok.domain.repository.HomeRepository
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

class HomeRepositoryImpl @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource
) : HomeRepository {

    override suspend fun getHomeMonthlyFeeds(date: String): SikdorokResponse<WeekFeed> {
        return homeRemoteDataSource.getHomeMonthlyFeeds(date)
    }

    override suspend fun getHomeDailyFeeds(
        page: Int,
        size: Int,
        date: String,
        tag: String?
    ): SikdorokResponse<HomeFeed> {
        return homeRemoteDataSource.getHomeDailyFeeds(page, size, date, tag)
    }

    override suspend fun getHomeListFeeds(
        size: Int,
        date: String,
        cursorDate: String?
    ): SikdorokResponse<ListFeed> {
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
