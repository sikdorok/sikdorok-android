package com.ddd.sikdorok.domain.withdraw

import com.ddd.sikdorok.domain.repository.UserWithDrawRepository
import com.ddd.sikdorok.shared.base.ApiResult
import javax.inject.Inject

class DeleteUserWithDrawUseCase @Inject constructor(
    private val withDrawRepository: UserWithDrawRepository
) {
    suspend operator fun invoke() : ApiResult<Unit> {
        return withDrawRepository.withdrawUser()
    }
}
