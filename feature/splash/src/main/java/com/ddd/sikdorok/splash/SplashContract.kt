package com.ddd.sikdorok.splash

import com.ddd.sikdorok.core_ui.base.BaseContract

interface SplashContract :
    BaseContract<SplashContract.State, SplashContract.Event, SplashContract.Effect> {

    data class State(
        val isLoading: Boolean = false
    )

    sealed interface Event {
        object DueTime : Event
        data class DeepLink(val deeplink: String) : Event

    }

    sealed interface Effect {
        data class GoToMain(val deeplink: String? = null) : Effect
    }
}