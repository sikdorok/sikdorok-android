package com.ddd.sikdorok.domain.repository

import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.modify.FeedRequest
import com.ddd.sikdorok.shared.modify.FeedResponse

interface ModifyRepository {
    suspend fun createFeed(file: String, body: FeedRequest): SikdorokResponse<String>

    suspend fun getFeed(feedId: String): SikdorokResponse<FeedResponse>

    suspend fun updateFeed(file: String, body: FeedRequest): SikdorokResponse<String>

    suspend fun deleteFeed(feedId: String): SikdorokResponse<Unit>
}
