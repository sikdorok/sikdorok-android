package com.ddd.sikdorok.domain.signup

import com.ddd.sikdorok.domain.repository.LoginRepository
import com.ddd.sikdorok.shared.sign.SignUp
import javax.inject.Inject

class PostSignUpUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(body: SignUp.Request) = loginRepository.onSignUpUser(body)
}
