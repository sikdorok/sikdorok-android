package com.ddd.sikdorok.domain.repository

import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.login.Response
import com.ddd.sikdorok.shared.sign.SignUp

interface LoginRepository {
    suspend fun onCheckSikdorokUser(code: String): SikdorokResponse<Response>

    suspend fun onSignUpUser(body: SignUp.Request): SikdorokResponse<Response>

}
