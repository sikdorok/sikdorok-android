package com.ddd.sikdorok.di

import com.ddd.sikdorok.find_password.FindPasswordNavigator
import com.ddd.sikdorok.home.HomeNavigator
import com.ddd.sikdorok.navigator.FindPasswordNavigatorImpl
import com.ddd.sikdorok.navigator.HomeNavigatorImpl
import com.ddd.sikdorok.navigator.SignInNavigatorImpl
import com.ddd.sikdorok.navigator.SignUpNavigatorImpl
import com.ddd.sikdorok.navigator.signin.SignInNavigator
import com.ddd.sikdorok.signup.SignUpNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
internal abstract class CoordinatorModule {
    @ActivityScoped
    @Binds
    abstract fun bindsSignInNavigator(navigator: SignInNavigatorImpl): SignInNavigator


    @ActivityScoped
    @Binds
    abstract fun bindsSignUpNavigator(navigator: SignUpNavigatorImpl): SignUpNavigator

    @ActivityScoped
    @Binds
    abstract fun bindsHomeNavigator(navigator: HomeNavigatorImpl): HomeNavigator

    @ActivityScoped
    @Binds
    abstract fun bindsFindPasswordNavigator(navigator: FindPasswordNavigatorImpl): FindPasswordNavigator
}
