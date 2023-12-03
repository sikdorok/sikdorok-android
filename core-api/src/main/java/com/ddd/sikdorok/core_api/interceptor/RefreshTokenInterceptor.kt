package com.ddd.sikdorok.core_api.interceptor

import com.ddd.sikdorok.core_api.annotation.RefreshInterceptor
import com.ddd.sikdorok.core_api.service.UserService
import com.ddd.sikdorok.data.SikdorokPreference
import com.ddd.sikdorok.shared.key.Keys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import com.ddd.sikdorok.shared.login.Request as SharedRequest


private const val AUTHENTICATION_FAILED = 401

@Suppress("SpellCheckingInspection")
internal class RefreshTokenInterceptor constructor(
    private val sikdorokPreference: SikdorokPreference,
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return if(response.code == AUTHENTICATION_FAILED) {
            val builder = response.newBuilder()
                .removeHeader("Authorization")
//                .addHeader("Authorization", "Bearer $accessToken")
                .build()

            builder.request
        } else {
            null
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal object RefreshTokenInterceptorModule {
    @Provides
    @RefreshInterceptor
    fun providesRefreshTokenInterceptor(
        preference: SikdorokPreference
    ): Authenticator {
        return RefreshTokenInterceptor(
            preference
        )
    }
}
