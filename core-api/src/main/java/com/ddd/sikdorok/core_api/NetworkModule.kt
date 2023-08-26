package com.ddd.sikdorok.core_api

import com.ddd.sikdorok.core_api.annotation.HeaderInterceptor
import com.ddd.sikdorok.core_api.annotation.LoggingInterceptor
import com.ddd.sikdorok.core_api.annotation.RefreshInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Provides
    @Singleton
    fun providesRetrofit(
        client: OkHttpClient
    ) = Retrofit.Builder()
        .baseUrl("https://sikdorok.jeffrey-oh.click")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesOkHttpClient(
        @LoggingInterceptor interceptor: Interceptor,
        @HeaderInterceptor accessTokenInterceptor: Interceptor,
        @RefreshInterceptor refreshTokenInterceptor: Authenticator
    ) = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(accessTokenInterceptor)
        .authenticator(refreshTokenInterceptor)
        .build()

    @Provides
    @Singleton
    @LoggingInterceptor
    fun providesHttpLoggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor {
            Timber.i(it)
        }

        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}

