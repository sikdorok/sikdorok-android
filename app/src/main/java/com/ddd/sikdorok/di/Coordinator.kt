package com.ddd.sikdorok.di

import com.ddd.sikdorok.find_password.FindPasswordNavigator
import com.ddd.sikdorok.home.HomeNavigator
import com.ddd.sikdorok.navigator.DeleteAccountNavigatorImpl
import com.ddd.sikdorok.navigator.FindPasswordNavigatorImpl
import com.ddd.sikdorok.navigator.HomeNavigatorImpl
import com.ddd.sikdorok.navigator.LoginNavigatorImpl
import com.ddd.sikdorok.navigator.ManagementNavigatorImpl
import com.ddd.sikdorok.navigator.SendPasswordNavigatorImpl
import com.ddd.sikdorok.navigator.SignInNavigatorImpl
import com.ddd.sikdorok.navigator.SignUpNavigatorImpl
import com.ddd.sikdorok.navigator.SplashNavigatorImpl
import com.ddd.sikdorok.navigator.delete_account.DeleteAccountNavigator
import com.ddd.sikdorok.navigator.login.LoginNavigator
import com.ddd.sikdorok.navigator.management.ManagementNavigator
import com.ddd.sikdorok.navigator.signin.SignInNavigator
import com.ddd.sikdorok.send_password.SendPasswordNavigator
import com.ddd.sikdorok.signup.SignUpNavigator
import com.ddd.sikdorok.splash.SplashNavigator
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

    @ActivityScoped
    @Binds
    abstract fun bindsSendPasswordNavigator(navigator: SendPasswordNavigatorImpl): SendPasswordNavigator

    @ActivityScoped
    @Binds
    abstract fun bindsSplashNavigator(navigator: SplashNavigatorImpl): SplashNavigator

    @ActivityScoped
    @Binds
    abstract fun bindsManagementNavigator(navigator: ManagementNavigatorImpl): ManagementNavigator

    @ActivityScoped
    @Binds
    abstract fun bindsDeleteAccountNavigator(navigator: DeleteAccountNavigatorImpl): DeleteAccountNavigator

    @ActivityScoped
    @Binds
    abstract fun bindsLoginNavigator(navigator: LoginNavigatorImpl): LoginNavigator
}
