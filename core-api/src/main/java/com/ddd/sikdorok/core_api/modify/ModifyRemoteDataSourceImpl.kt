package com.ddd.sikdorok.core_api.modify

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.ddd.sikdorok.core_api.service.ModifyService
import com.ddd.sikdorok.data.modify.data.ModifyRemoteDataSource
import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.modify.FeedRequest
import com.ddd.sikdorok.shared.modify.FeedResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

internal class ModifyRemoteDataSourceImpl @Inject constructor(
    private val createService: ModifyService.Create,
    private val readService: ModifyService.Read,
    private val updateService: ModifyService.Update,
    private val deleteService: ModifyService.Delete
) : ModifyRemoteDataSource {

    override suspend fun createFeed(file: ByteArray, body: FeedRequest): SikdorokResponse<String> {
        return createService.createFeed(
            file = createMultipartBody(convertByteArrayToPngFile(file)),
            feedBody = body
        )
    }

    override suspend fun getFeed(feedId: String): SikdorokResponse<FeedResponse> {
        return readService.getFeed(feedId)
    }

    override suspend fun updateFeed(file: ByteArray, body: FeedRequest): SikdorokResponse<String> {
        return updateService.updateFeed(
            file = createMultipartBody(convertByteArrayToPngFile(file)),
            feedBody = body
        )
    }

    override suspend fun deleteFeed(feedId: String): SikdorokResponse<Unit> {
        return deleteService.deleteFeed(feedId)
    }

    private fun convertByteArrayToPngFile(byteArray: ByteArray): File {
        val imageFile = File.createTempFile("IMG_", ".png")

        try {
            FileOutputStream(imageFile).use { stream ->
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                bitmap.compress(
                    Bitmap.CompressFormat.PNG,
                    100,
                    stream
                )
            }
        } catch (_: Exception) {
        }

        return imageFile
    }

    private fun createMultipartBody(file: File): MultipartBody.Part {
        val MEDIA_TYPE_IMAGE = "image/png"

        return MultipartBody.Part.createFormData(
            "file",
            "profile.png",
            file.asRequestBody(MEDIA_TYPE_IMAGE.toMediaType())
        )
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
