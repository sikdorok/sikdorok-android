package com.ddd.sikdorok.core_api

import com.ddd.sikdorok.core_api.service.ModifyService
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
        retrofit: Retrofit
    ) = retrofit.create<UserService.WithDraw>()

    @Provides
    @Singleton
    fun providesSikdorokLogin(
       retrofit: Retrofit
    ) = retrofit.create<UserService.SikdorokLogin>()

    @Provides
    @Singleton
    fun providesSDRSignUp(
        retrofit: Retrofit
    ) = retrofit.create<UserService.SignUp>()


    @Provides
    @Singleton
    fun providesSDREmailValidate(
        retrofit: Retrofit
    ) = retrofit.create<UserService.EmailCheck>()

    @Provides
    @Singleton
    fun providesSDRFindPassword(
        retrofit: Retrofit
    ) = retrofit.create<UserService.FindPassword>()

    @Provides
    @Singleton
    fun providesSDRCreateFeed(
        retrofit: Retrofit
    ) = retrofit.create<ModifyService.Create>()


    @Provides
    @Singleton
    fun providesSDRUpdateFeed(
        retrofit: Retrofit
    ) = retrofit.create<ModifyService.Update>()

    @Provides
    @Singleton
    fun providesSDRDeleteFeed(
        retrofit: Retrofit
    ) = retrofit.create<ModifyService.Delete>()
}
