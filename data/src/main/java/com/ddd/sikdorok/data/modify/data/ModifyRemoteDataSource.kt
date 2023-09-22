package com.ddd.sikdorok.data.modify.data

import android.net.Uri
import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.modify.FeedRequest
import com.ddd.sikdorok.shared.modify.FeedResponse

interface ModifyRemoteDataSource {
    suspend fun createFeed(file: Uri, body: FeedRequest): SikdorokResponse<String>

    suspend fun getFeed(feedId: String): SikdorokResponse<FeedResponse>

    suspend fun updateFeed(file: Uri, body: FeedRequest): SikdorokResponse<String>

    suspend fun deleteFeed(feedId: String): SikdorokResponse<Unit>
}
