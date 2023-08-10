package com.ddd.sikdorok.domain.password

import com.ddd.sikdorok.domain.repository.PasswordRepository
import javax.inject.Inject

class PostFindPasswordUseCase @Inject constructor(
    private val passwordRepository: PasswordRepository
){
    suspend operator fun invoke(value: String) = passwordRepository.findPassword(value)
}
