package com.ddd.sikdorok.domain.email

import com.ddd.sikdorok.domain.repository.LoginRepository
import javax.inject.Inject

class PostOnCheckEmailUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(email: String) = loginRepository.onCheckSikdorokEmail(email)
}
