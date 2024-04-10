package com.ddd.sikdorok.domain.home

import com.ddd.sikdorok.domain.repository.HomeRepository
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.home.ListFeedRes
import javax.inject.Inject

class GetHomeListFeedsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(
        size: Int,
        date: String,
        cursorDate: String?
    ): ApiResult<ListFeedRes> {
        return homeRepository.getHomeListFeeds(size, date, cursorDate)
    }
}