package com.ddd.sikdorok.domain.home

import com.ddd.sikdorok.domain.repository.HomeRepository
import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.home.WeekFeed
import javax.inject.Inject

class GetHomeMonthlyFeedsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(date: String): SikdorokResponse<WeekFeed> {
        return homeRepository.getHomeMonthlyFeeds(date)
    }
}