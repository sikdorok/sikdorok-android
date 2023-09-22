package com.ddd.sikdorok.core_api.modify

import android.net.Uri
import com.ddd.sikdorok.core_api.service.ModifyService
import com.ddd.sikdorok.data.modify.data.ModifyRemoteDataSource
import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.modify.FeedRequest
import com.ddd.sikdorok.shared.modify.FeedResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Singleton

internal class ModifyRemoteDataSourceImpl constructor(
    private val createService: ModifyService.Create,
    private val readService: ModifyService.Read,
    private val updateService: ModifyService.Update,
    private val deleteService: ModifyService.Delete
) : ModifyRemoteDataSource {

    override suspend fun createFeed(file: Uri, body: FeedRequest): SikdorokResponse<String> {
        return createService.createFeed(
            file = file.toString().toRequestBody(MEDIA_TYPE.toMediaTypeOrNull()),
            feedBody = body
        )
    }

    override suspend fun getFeed(feedId: String): SikdorokResponse<FeedResponse> {
        return readService.getFeed(feedId)
    }

    override suspend fun updateFeed(file: Uri, body: FeedRequest): SikdorokResponse<String> {
        return updateService.updateFeed(
            file = file.toString().toRequestBody(MEDIA_TYPE.toMediaTypeOrNull()),
            feedBody = body
        )
    }

    override suspend fun deleteFeed(feedId: String): SikdorokResponse<Unit> {
        return deleteService.deleteFeed(feedId)
    }

    companion object {
        private const val MEDIA_TYPE = "multipart/form-data"
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal object ModifyRemoteDataSourceModule {
    @Singleton
    @Provides
    fun providesModifyRemoteDataSource(
        createService: ModifyService.Create,
        readService: ModifyService.Read,
        updateService: ModifyService.Update,
        deleteService: ModifyService.Delete
    ): ModifyRemoteDataSource {
        return ModifyRemoteDataSourceImpl(createService, readService, updateService, deleteService)
    }
}
