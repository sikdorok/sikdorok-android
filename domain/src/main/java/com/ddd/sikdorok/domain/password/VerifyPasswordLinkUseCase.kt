package com.ddd.sikdorok.domain.password

import com.ddd.sikdorok.domain.repository.PasswordRepository
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.password.Reset
import com.ddd.sikdorok.shared.password.Verify
import dagger.Reusable
import javax.inject.Inject

@Reusable
class VerifyPasswordLinkUseCase @Inject constructor(
    private val passwordRepository: PasswordRepository
) {
    suspend operator fun invoke(body: Verify.Request): ApiResult<Boolean> {
        return passwordRepository.verifyPassword(body)
    }

}