package com.ddd.sikdorok.signin

class SignInContract {
    data class State(
        val email: String = "",
        val password: String = ""
    )

    sealed class Event {
        data class OnClickSubmit(val email: String, val password: String) : Event()

        object NaviToSignUp : Event()
        object NaviToFindPassword : Event()

        object OnBackPressed : Event()
    }

    sealed class SideEffect {
        object NaviToSignUp : SideEffect()
        object NaviToFindPassword : SideEffect()

        object NaviToBack : SideEffect()
        object NaviToHome: SideEffect()

        data class ShowSnackBar(val message: String) : SideEffect()
    }
}
