package com.ddd.sikdorok.data.login.data

import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.login.LoginRes
import com.ddd.sikdorok.shared.login.Request
import com.ddd.sikdorok.shared.login.Response
import com.ddd.sikdorok.shared.sign.SignUp

@Suppress("SpellCheckingInspection")
interface LoginRemoteDataSource {
    suspend fun onCheckSikdorokEmail(email: String): ApiResult<Boolean>

    suspend fun onCheckSikdorokUser(code: String): ApiResult<LoginRes>

    suspend fun onRequestSikdorokLocalUser(body: Request.Sikdorok): ApiResult<LoginRes>

    suspend fun onSignUpUser(body: SignUp.Request): ApiResult<LoginRes>
}
