package com.ddd.sikdorok.reset_password

class ResetPasswordContract {
    data class State(
        val password: String = "",
        val confirmPassword: String = "",
        val isLoading: Boolean = false
    )

    sealed interface Event {
        data class InputPassword(val password : String) : Event
        data class InputPasswordConfirm(val password : String) : Event

        object OnClickLeftIcon : Event

        object Submit : Event
    }

    sealed interface SideEffect {
        object NaviToBack : SideEffect
        object NaviToSuccess : SideEffect

        object InValidatePassword : SideEffect
        object ValidatePassword : SideEffect

        data class ShowSnackBar(val message: String) : SideEffect
    }
}
