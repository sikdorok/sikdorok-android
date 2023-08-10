package com.ddd.sikdorok.find_password

class FindPasswordContract {
    data class State(
        val email: String = ""
    )

    sealed class Event {
        object OnClickLeftIcon : Event()

        data class InputEmail(val value: String) : Event()

        data class Submit(val email: String) : Event()
    }

    sealed class SideEffect {
        object NaviToBack : SideEffect()
        object NaviToSuccess : SideEffect()

        object InValidateEmail : SideEffect()
        object ValidateEmail : SideEffect()

        data class ShowSnackBar(val message: String): SideEffect()
    }
}
