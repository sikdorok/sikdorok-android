package com.ddd.sikdorok.domain.home

import com.ddd.sikdorok.domain.repository.HomeRepository
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.home.HomeFeedRes
import javax.inject.Inject

class GetHomeDailyFeedsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(
        page: Int,
        size: Int,
        date: String,
        tag: String? = null
    ): ApiResult<HomeFeedRes> {
        return homeRepository.getHomeDailyFeeds(page, size, date, tag)
    }
}