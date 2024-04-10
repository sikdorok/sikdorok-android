package com.ddd.sikdorok.domain.modify

import com.ddd.sikdorok.domain.repository.ModifyRepository
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.modify.FeedRes
import javax.inject.Inject

class ReadFeedUseCase @Inject constructor(
    private val modifyRepository: ModifyRepository
) {
    suspend operator fun invoke(feedId: String): ApiResult<FeedRes> {
        return modifyRepository.getFeed(feedId)
    }
}
