package com.ddd.sikdorok.domain.home

import com.ddd.sikdorok.domain.repository.HomeRepository
import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.home.ListFeed
import javax.inject.Inject

class GetHomeListFeedsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(
        size: Int,
        date: String,
        cursorDate: String?
    ): SikdorokResponse<ListFeed> {
        return homeRepository.getHomeListFeeds(size, date, cursorDate)
    }
}