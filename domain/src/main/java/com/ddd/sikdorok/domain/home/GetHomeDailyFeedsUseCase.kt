package com.ddd.sikdorok.domain.home

import com.ddd.sikdorok.domain.repository.HomeRepository
import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.home.HomeFeed
import javax.inject.Inject

class GetHomeDailyFeedsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(
        page: Int,
        size: Int,
        date: String,
        tag: String? = null
    ): SikdorokResponse<HomeFeed> {
        return homeRepository.getHomeDailyFeeds(page, size, date, tag)
    }
}