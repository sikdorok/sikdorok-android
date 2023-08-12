package com.ddd.sikdorok.domain.repository

import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.login.Response
import com.ddd.sikdorok.shared.login.TokenType
import com.ddd.sikdorok.shared.sign.SignUp

@Suppress("SpellCheckingInspection")
interface LoginRepository {

    suspend fun onCheckSikdorokEmail(email: String): SikdorokResponse<Boolean>

    suspend fun onCheckSikdorokUser(code: String): SikdorokResponse<Response>

    suspend fun onSignUpUser(body: SignUp.Request): SikdorokResponse<Response>

    fun onPostSaveToken(type: TokenType, token: String): Result<Unit>
}
