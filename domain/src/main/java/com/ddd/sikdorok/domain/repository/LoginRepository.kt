package com.ddd.sikdorok.domain.repository

import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.base.BaseResponse
import com.ddd.sikdorok.shared.login.CheckUserRes
import com.ddd.sikdorok.shared.login.LoginRes
import com.ddd.sikdorok.shared.login.RefreshTokenRes
import com.ddd.sikdorok.shared.login.Request
import com.ddd.sikdorok.shared.login.TokenType
import com.ddd.sikdorok.shared.sign.SignUp

@Suppress("SpellCheckingInspection")
interface LoginRepository {

    suspend fun onCheckSikdorokEmail(email: String): ApiResult<CheckUserRes>

    suspend fun onCheckSikdorokUser(code: String): ApiResult<LoginRes>

    suspend fun onRequestSikdorokLocalUser(body: Request.Sikdorok): ApiResult<LoginRes>

    suspend fun onSignUpUser(body: SignUp.Request): ApiResult<LoginRes>

    fun onPostSaveToken(type: TokenType, token: String)

    fun onGetSavedToken(key: String): String

    suspend fun postRefreshToken(refreshToken : String) : RefreshTokenRes
}
