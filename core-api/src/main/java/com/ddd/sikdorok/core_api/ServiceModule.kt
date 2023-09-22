package com.ddd.sikdorok.core_api

import com.ddd.sikdorok.core_api.annotation.NoAuthRetrofit
import com.ddd.sikdorok.core_api.annotation.NormalRetrofit
import com.ddd.sikdorok.core_api.service.HomeService
import com.ddd.sikdorok.core_api.service.ModifyService
import com.ddd.sikdorok.core_api.service.SettingsService
import com.ddd.sikdorok.core_api.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {

    @Provides
    @Singleton
    fun providesSikdorokWithDraw(
        @NormalRetrofit retrofit: Retrofit
    ) = retrofit.create<UserService.WithDraw>()

    @Provides
    @Singleton
    fun providesSikdorokLogin(
        @NoAuthRetrofit retrofit: Retrofit
    ) = retrofit.create<UserService.SikdorokLogin>()

    @Provides
    @Singleton
    fun providesSikdorokRefreshToken(
        @NormalRetrofit retrofit: Retrofit
    ) = retrofit.create<UserService.Token>()

    @Provides
    @Singleton
    fun providesSDRSignUp(
        @NoAuthRetrofit retrofit: Retrofit
    ) = retrofit.create<UserService.SignUp>()


    @Provides
    @Singleton
    fun providesSDREmailValidate(
        @NoAuthRetrofit retrofit: Retrofit
    ) = retrofit.create<UserService.EmailCheck>()

    @Provides
    @Singleton
    fun providesSDRFindPassword(
        @NoAuthRetrofit retrofit: Retrofit
    ) = retrofit.create<UserService.FindPassword>()

    @Provides
    @Singleton
    fun providesSDRCreateFeed(
        @NormalRetrofit retrofit: Retrofit
    ) = retrofit.create<ModifyService.Create>()


    @Provides
    @Singleton
    fun providesSDRUpdateFeed(
        @NormalRetrofit retrofit: Retrofit
    ) = retrofit.create<ModifyService.Update>()

    @Provides
    @Singleton
    fun providesSDRDeleteFeed(
        @NormalRetrofit retrofit: Retrofit
    ) = retrofit.create<ModifyService.Delete>()

    @Provides
    @Singleton
    fun providesSDRHomeService(
        @NormalRetrofit retrofit: Retrofit
    ) = retrofit.create<HomeService>()

    @Provides
    @Singleton
    fun providesSDRSettingsService(
        @NormalRetrofit retrofit: Retrofit
    ) = retrofit.create<SettingsService>()

}
