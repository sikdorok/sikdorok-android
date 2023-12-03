package com.ddd.sikdorok.domain.login

import com.ddd.sikdorok.domain.repository.LoginRepository
import javax.inject.Inject

class PostOnCheckUserUseCase @Inject constructor(
    private val loginRepository: LoginRepository
){
    suspend operator fun invoke(code: String) = loginRepository.onCheckSikdorokUser(code)
}