package com.ddd.sikdorok.domain.password

import com.ddd.sikdorok.domain.repository.PasswordRepository
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.login.CheckUserRes
import com.ddd.sikdorok.shared.password.Verify
import dagger.Reusable
import javax.inject.Inject

@Reusable
class VerifyPasswordLinkUseCase @Inject constructor(
    private val passwordRepository: PasswordRepository
) {
    suspend operator fun invoke(body: Verify.Request): ApiResult<CheckUserRes> {
        return passwordRepository.verifyPassword(body)
    }

}