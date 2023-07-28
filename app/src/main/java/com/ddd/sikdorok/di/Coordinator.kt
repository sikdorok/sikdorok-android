package com.ddd.sikdorok.di

import com.ddd.sikdorok.navigator.SignInNavigatorImpl
import com.ddd.sikdorok.navigator.signin.SignInNavigator
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
}
