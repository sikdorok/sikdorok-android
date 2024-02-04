package com.ddd.sikdorok.core_api.interceptor

import com.ddd.sikdorok.core_api.NetworkModule
import com.ddd.sikdorok.core_api.calladapter.ResultCallAdapterFactory
import com.ddd.sikdorok.core_api.service.UserService
import com.ddd.sikdorok.data.SikdorokPreference
import com.ddd.sikdorok.shared.key.Keys
import com.ddd.sikdorok.shared.login.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject

@Suppress("SpellCheckingInspection")
internal class AccessTokenInterceptor @Inject constructor(
    private val sikdorokPreference: SikdorokPreference
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        val accessToken = sikdorokPreference.getString(Keys.ACCESS_TOKEN)

        val header = builder
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        val response = chain.proceed(header)

        return if (response.code == 401) {
            runBlocking(Dispatchers.IO) {
                val refreshToken = sikdorokPreference.getString(Keys.REFRESH_TOKEN)
                val newAccessToken = getRefreshToken(refreshToken).data

                response.close()

                sikdorokPreference.savePref(Keys.ACCESS_TOKEN, newAccessToken)
                builder.removeHeader("Authorization")
                builder.addHeader("Authorization", "Bearer $newAccessToken")

                chain.proceed(builder.build())
            }
        } else {
            response
        }
    }

    // token refresh 호출
    private suspend fun getRefreshToken(
        accessToken: String
    ) = Retrofit.Builder()
        .baseUrl(NetworkModule.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(ResultCallAdapterFactory())
        .build()
        .create<UserService.Token>().postRefreshToken(
            Request.RefreshToken(accessToken)
        )
}