package com.ddd.sikdorok.domain.modify

import com.ddd.sikdorok.domain.repository.ModifyRepository
import javax.inject.Inject

class DeleteFeedUseCase @Inject constructor(
    private val modifyRepository: ModifyRepository
) {
    suspend operator fun invoke(feedId: String) = modifyRepository.deleteFeed(feedId)
}
