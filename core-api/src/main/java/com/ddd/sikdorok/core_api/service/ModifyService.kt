package com.ddd.sikdorok.core_api.service

import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.base.BaseResponse
import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.modify.CreateFeedRes
import com.ddd.sikdorok.shared.modify.FeedRequest
import com.ddd.sikdorok.shared.modify.FeedRes
import com.ddd.sikdorok.shared.modify.FeedResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

interface ModifyService {
    interface Create {
        @Multipart
        @POST("/feed")
        suspend fun createFeed(
            @Part file: MultipartBody.Part?,
            @Part("request") feedBody: FeedRequest
        ): ApiResult<CreateFeedRes>
    }

    interface Read {
        @GET("/feed/{feedId}")
        suspend fun getFeed(
            @Path("feedId") feedId: String,
        ): ApiResult<FeedRes>
    }

    interface Update {
        @Multipart
        @PUT("/feed")
        suspend fun updateFeed(
            @Part file: MultipartBody.Part?,
            @Part("request") feedBody: FeedRequest
        ): ApiResult<CreateFeedRes>
    }


    interface Delete {
        @DELETE("/feed/{feedId}")
        suspend fun deleteFeed(
            @Path("feedId") feedId: String
        ): ApiResult<BaseResponse>
    }
}
