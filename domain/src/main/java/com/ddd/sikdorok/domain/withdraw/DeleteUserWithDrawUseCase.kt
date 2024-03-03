package com.ddd.sikdorok.domain.withdraw

import com.ddd.sikdorok.domain.repository.UserWithDrawRepository
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.base.BaseResponse
import javax.inject.Inject

class DeleteUserWithDrawUseCase @Inject constructor(
    private val withDrawRepository: UserWithDrawRepository
) {
    suspend operator fun invoke() : ApiResult<BaseResponse> {
        return withDrawRepository.withdrawUser()
    }
}
