package com.ddd.sikdorok.data.modify.data

import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.base.BaseResponse
import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.modify.CreateFeedRes
import com.ddd.sikdorok.shared.modify.FeedRequest
import com.ddd.sikdorok.shared.modify.FeedRes
import com.ddd.sikdorok.shared.modify.FeedResponse

interface ModifyRemoteDataSource {
    suspend fun createFeed(file: ByteArray?, body: FeedRequest): ApiResult<CreateFeedRes>

    suspend fun getFeed(feedId: String): ApiResult<FeedRes>

    suspend fun updateFeed(file: ByteArray?, body: FeedRequest): ApiResult<CreateFeedRes>

    suspend fun deleteFeed(feedId: String): ApiResult<BaseResponse>
}
