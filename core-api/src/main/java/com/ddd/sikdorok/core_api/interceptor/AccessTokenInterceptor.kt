package com.ddd.sikdorok.core_api.interceptor

import com.ddd.sikdorok.core_api.annotation.HeaderInterceptor
import com.ddd.sikdorok.data.SikdorokPreference
import com.ddd.sikdorok.shared.key.Keys
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("SpellCheckingInspection")
internal class AccessTokenInterceptor @Inject constructor(
    private val sikdorokPreference: SikdorokPreference
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        val header = builder
            .addHeader("Authorization", "Bearer ${sikdorokPreference.getString(Keys.ACCESS_TOKEN)}")
            .build()

        return chain.proceed(header)
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AccessTokenInterceptorModule {
    @Binds
    @Singleton
    @HeaderInterceptor
    abstract fun bindsAccessTokenInterceptor(interceptor: AccessTokenInterceptor): Interceptor
}
