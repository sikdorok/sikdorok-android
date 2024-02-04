package com.ddd.sikdorok.domain.withdraw

import com.ddd.sikdorok.domain.repository.UserWithDrawRepository
import javax.inject.Inject

class DeleteUserAllSettingsUseCase @Inject constructor(
    private val withDrawRepository: UserWithDrawRepository
) {
    operator fun invoke(): Boolean {
        return withDrawRepository.deleteAllData()
    }
}
