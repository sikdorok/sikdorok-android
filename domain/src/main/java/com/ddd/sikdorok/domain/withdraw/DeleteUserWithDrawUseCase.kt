package com.ddd.sikdorok.domain.withdraw

import com.ddd.sikdorok.domain.repository.UserWithDrawRepository
import javax.inject.Inject

class DeleteUserWithDrawUseCase @Inject constructor(
    private val withDrawRepository: UserWithDrawRepository
) {
    suspend operator fun invoke() = withDrawRepository.withdrawUser()
}
