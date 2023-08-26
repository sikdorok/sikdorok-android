package com.ddd.sikdorok.data.login.data

import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.login.Request
import com.ddd.sikdorok.shared.login.Response
import com.ddd.sikdorok.shared.sign.SignUp

@Suppress("SpellCheckingInspection")
interface LoginRemoteDataSource {
    suspend fun onCheckSikdorokEmail(email: String): SikdorokResponse<Boolean>

    suspend fun onCheckSikdorokUser(code: String): SikdorokResponse<Response>

    suspend fun onRequestSikdorokLocalUser(body: Request.Sikdorok): SikdorokResponse<Response>

    suspend fun onSignUpUser(body: SignUp.Request): SikdorokResponse<Response>
}
