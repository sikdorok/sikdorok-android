package com.ddd.sikdorok.login


class LoginContract {

    data class State(
        val showLoading: Boolean = false
    )

    sealed class Event {
        object RequestKakaoLogin : Event()
        object RequestSikdorokLogin : Event()

        data class CheckKakaoUser(val code: String) : Event()
    }

    sealed class SideEffect {
        object NaviToKakaoLogin : SideEffect()
        object NaviToSikdorokLogin : SideEffect()

        data class NaviToSignUp(
            val email: String? = null,
            val oauthId: Long? = null,
            val oauthType: String? = null
        ) : SideEffect()

        object NaviToHome : SideEffect()

        data class ShowSnackBar(val message: String) : SideEffect()
    }
}