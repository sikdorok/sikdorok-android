package com.ddd.sikdorok.core_api.interceptor

import android.content.Context
import com.ddd.sikdorok.core_api.annotation.FlipperInterceptor
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FlipperInterceptorModule {

    @Provides
    @Singleton
    @FlipperInterceptor
    fun providesFlipperInterceptor(
        @ApplicationContext context: Context
    ): Interceptor {
        val interceptor = FlipperOkhttpInterceptor(
            AndroidFlipperClient.getInstance(context).getPlugin(NetworkFlipperPlugin.ID)
        )
        return interceptor
    }
}
