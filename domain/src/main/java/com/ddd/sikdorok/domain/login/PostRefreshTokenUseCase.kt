package com.ddd.sikdorok.domain.login

import com.ddd.sikdorok.domain.repository.LoginRepository
import javax.inject.Inject

class PostRefreshTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(refreshToken: String) =
        loginRepository.postRefreshToken(refreshToken)

}
