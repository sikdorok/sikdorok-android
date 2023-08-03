package com.ddd.sikdorok.data.login.data

import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.login.Response
import com.ddd.sikdorok.shared.sign.SignUp

interface LoginRemoteDataSource {
    suspend fun onCheckSikdorokUser(code: String): SikdorokResponse<Response>

    suspend fun onSignUpUser(body: SignUp.Request): SikdorokResponse<Response>
}
