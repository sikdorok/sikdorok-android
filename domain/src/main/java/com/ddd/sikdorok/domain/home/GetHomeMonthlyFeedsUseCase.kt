package com.ddd.sikdorok.domain.home

import com.ddd.sikdorok.domain.repository.HomeRepository
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.home.WeekFeedRes
import javax.inject.Inject

class GetHomeMonthlyFeedsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(date: String): ApiResult<WeekFeedRes> {
        return homeRepository.getHomeMonthlyFeeds(date)
    }
}