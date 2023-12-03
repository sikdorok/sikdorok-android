package com.ddd.sikdorok.data.modify

import com.ddd.sikdorok.data.modify.data.ModifyRemoteDataSource
import com.ddd.sikdorok.domain.repository.ModifyRepository
import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.modify.FeedRequest
import com.ddd.sikdorok.shared.modify.FeedResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

internal class ModifyRepositoryImpl @Inject constructor(
    private val modifyRemoteDataSource: ModifyRemoteDataSource
) : ModifyRepository {
    override suspend fun getFeed(feedId: String): SikdorokResponse<FeedResponse> {
        return modifyRemoteDataSource.getFeed(feedId)
    }

    override suspend fun createFeed(file: ByteArray, body: FeedRequest): SikdorokResponse<String> {
        return modifyRemoteDataSource.createFeed(file, body)
    }

    override suspend fun updateFeed(file: ByteArray, body: FeedRequest): SikdorokResponse<String> {
        return modifyRemoteDataSource.updateFeed(file, body)
    }

    override suspend fun deleteFeed(feedId: String): SikdorokResponse<Unit> {
        return modifyRemoteDataSource.deleteFeed(feedId)
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ModifyRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsModifyRepository(repository: ModifyRepositoryImpl): ModifyRepository
}

