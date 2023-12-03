package com.ddd.sikdorok.domain.login

import com.ddd.sikdorok.domain.repository.LoginRepository
import javax.inject.Inject

class GetSavedTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    operator fun invoke(key: String) = loginRepository.onGetSavedToken(key)
}
