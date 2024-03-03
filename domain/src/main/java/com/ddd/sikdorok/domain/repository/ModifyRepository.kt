package com.ddd.sikdorok.domain.repository

import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.base.BaseResponse
import com.ddd.sikdorok.shared.modify.CreateFeedRes
import com.ddd.sikdorok.shared.modify.FeedRes
import com.ddd.sikdorok.shared.modify.FeedRequest

interface ModifyRepository {
    suspend fun createFeed(file: ByteArray?, body: FeedRequest): ApiResult<CreateFeedRes>

    suspend fun getFeed(feedId: String): ApiResult<FeedRes>

    suspend fun updateFeed(file: ByteArray?, body: FeedRequest): ApiResult<CreateFeedRes>

    suspend fun deleteFeed(feedId: String): ApiResult<BaseResponse>
}
