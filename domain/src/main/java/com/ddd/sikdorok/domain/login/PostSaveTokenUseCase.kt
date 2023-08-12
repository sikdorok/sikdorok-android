package com.ddd.sikdorok.domain.login

import com.ddd.sikdorok.domain.repository.LoginRepository
import com.ddd.sikdorok.shared.login.TokenType
import javax.inject.Inject

class PostSaveTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    operator fun invoke(type: TokenType, token: String) = loginRepository.onPostSaveToken(type, token)
}
