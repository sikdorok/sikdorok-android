package com.ddd.sikdorok.signup

class SignUpContract {
    data class State(
        val name: String = "",
        val email: String = "",
        val password: String = "",
        val passwordCheck: String = ""
    ) {
        val isValidate: Boolean
            get() {
                return name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
                        && passwordCheck.isNotEmpty()
            }
    }

    sealed class Event {
        data class EmailCheck(val email: String) : Event()

        data class InputName(val name: String) : Event()

        data class InputPassword(val password: String) : Event()

        data class InputPasswordCheck(val password: String) : Event()

        data class PasswordCheck(val isSame: Boolean) : Event()

        data class SignUp(
            val nickname: String,
            val email: String,
            val password: String,
            val passwordCheck: String
        ) : Event()

        object OnBackPressed : Event()
    }

    sealed class SideEffect {
        object InValidateName : SideEffect()
        object ValidateName : SideEffect()

        object InValidatePassword : SideEffect()
        object ValidatePassword : SideEffect()

        object InValidatePasswordCheck : SideEffect()
        object ValidatePasswordCheck : SideEffect()

        object NaviToHome : SideEffect()
        object NaviToBack : SideEffect()
    }
}
