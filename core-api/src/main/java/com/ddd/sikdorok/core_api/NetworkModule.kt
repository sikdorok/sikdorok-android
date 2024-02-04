package com.ddd.sikdorok.core_api

import android.content.Context
import com.ddd.sikdorok.core_api.annotation.AccessManagerRetrofit
import com.ddd.sikdorok.core_api.annotation.FlipperInterceptor
import com.ddd.sikdorok.core_api.annotation.HeaderInterceptor
import com.ddd.sikdorok.core_api.annotation.LoggingInterceptor
import com.ddd.sikdorok.core_api.annotation.NoAuthOkHttpClient
import com.ddd.sikdorok.core_api.annotation.NoAuthRetrofit
import com.ddd.sikdorok.core_api.annotation.NormalOkHttpClient
import com.ddd.sikdorok.core_api.annotation.NormalRetrofit
import com.ddd.sikdorok.core_api.calladapter.ResultCallAdapterFactory
import com.ddd.sikdorok.core_api.interceptor.AccessTokenInterceptor
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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

    const val BASE_URL = "https://sikdorok.jeffrey-oh.click"
    private val gson: Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    @NormalRetrofit
    fun providesNormalRetrofit(
        @NormalOkHttpClient client: OkHttpClient
    ) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(ResultCallAdapterFactory())
        .build()

    @Provides
    @Singleton
    @NoAuthRetrofit
    fun providesNoAuthRetrofit(
        @NoAuthOkHttpClient client: OkHttpClient
    ) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addCallAdapterFactory(ResultCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    @AccessManagerRetrofit
    fun provideAccessManagerRetrofit(
        @ApplicationContext context: Context,
    ) = OkHttpClient.Builder()
        .addNetworkInterceptor(
            FlipperOkhttpInterceptor(
                AndroidFlipperClient.getInstance(context).getPlugin(NetworkFlipperPlugin.ID)
            )
        ).build()

    // OKHttpClients
    @Provides
    @Singleton
    @NormalOkHttpClient
    fun providesNormalOkHttpClient(
        @LoggingInterceptor interceptor: Interceptor,
        @HeaderInterceptor accessTokenInterceptor: Interceptor,
        @FlipperInterceptor flipperInterceptor: Interceptor,
    ) = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(accessTokenInterceptor)
        .addNetworkInterceptor(flipperInterceptor)
        .build()

    @Provides
    @Singleton
    @NoAuthOkHttpClient
    fun providesNoAuthOkHttpClient(
        @ApplicationContext context: Context,
        @LoggingInterceptor interceptor: Interceptor
    ) = OkHttpClient.Builder()
        .addNetworkInterceptor(
            FlipperOkhttpInterceptor(
                AndroidFlipperClient.getInstance(context).getPlugin(NetworkFlipperPlugin.ID)
            )
        )
        .addInterceptor(interceptor)
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

    @Provides
    @Singleton
    @HeaderInterceptor
    fun providesAccessTokenInterceptor(
        accessTokenInterceptor: AccessTokenInterceptor
    ): Interceptor {
        return accessTokenInterceptor
    }
}

