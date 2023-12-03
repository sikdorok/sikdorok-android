package com.ddd.sikdorok.domain.login

import com.ddd.sikdorok.domain.repository.LoginRepository
import com.ddd.sikdorok.shared.login.Request
import javax.inject.Inject

@Suppress("SpellCheckingInspection")
class PostSikdorokLocalLoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(body: Request.Sikdorok) = runCatching {
        loginRepository.onRequestSikdorokLocalUser(body)
    }
}
